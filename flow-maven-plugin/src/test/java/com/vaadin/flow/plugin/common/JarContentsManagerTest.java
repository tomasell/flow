/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.flow.plugin.common;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import nl.jqno.equalsverifier.internal.exceptions.AssertionException;
import nl.jqno.equalsverifier.internal.util.Formatter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.vaadin.flow.plugin.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Vaadin Ltd.
 */
public class JarContentsManagerTest {
    @Rule
    public TemporaryFolder testDirectory = new TemporaryFolder();

    private final JarContentsManager jarContentsManager = new JarContentsManager();
    private final File testJar = TestUtils.getTestJar();

    @Test(expected = IllegalArgumentException.class)
    public void getFileContents_directoryInsteadOfJar() {
        jarContentsManager.getFileContents(testDirectory.getRoot(), "test");
    }

    @Test(expected = UncheckedIOException.class)
    public void getFileContents_notAJarFile() throws IOException {
        File testFile = testDirectory.newFile("test");
        jarContentsManager.getFileContents(testFile, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFileContents_nonExistingJarFile() {
        jarContentsManager.getFileContents(new File("test"), "test");
    }

    @Test
    public void getFileContents_nonExistingFile() {
        byte[] fileContents = jarContentsManager.getFileContents(testJar, "blah");

        assertNull("Expect to have non-empty file from jar", fileContents);
    }

    @Test
    public void getFileContents_existingFile() {
        byte[] fileContents = jarContentsManager.getFileContents(testJar, "META-INF/resources/webjars/paper-button/2.0.0/bower.json");

        assertNotNull("Expect to have non-empty file from jar", fileContents);
        assertTrue("Expect to have non-empty file from jar", fileContents.length > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsPath_directoryInsteadOfJar() {
        jarContentsManager.containsPath(testDirectory.getRoot(), "test");
    }

    @Test(expected = UncheckedIOException.class)
    public void containsPath_notAJarFile() throws IOException {
        File testFile = testDirectory.newFile("test");
        jarContentsManager.containsPath(testFile, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsPath_nonExistingJarFile() {
        jarContentsManager.containsPath(new File("test"), "test");
    }

    @Test
    public void containsPath_nonExistingPath() {
        String nonExistingPath = "should not exist";

        assertFalse(String.format("Test jar '%s' should not contain path '%s'", testJar, nonExistingPath), jarContentsManager.containsPath(testJar, nonExistingPath));
    }

    @Test
    public void containsPath_existingFile() {
        String existingPath = "META-INF/resources/webjars/";

        assertTrue(String.format("Test jar '%s' should contain path '%s'", testJar, existingPath), jarContentsManager.containsPath(testJar, existingPath));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFiles_directoryInsteadOfJar() {
        jarContentsManager.findFiles(testDirectory.getRoot(), "test", "test");
    }

    @Test(expected = UncheckedIOException.class)
    public void findFiles_notAJarFile() throws IOException {
        File testFile = testDirectory.newFile("test");
        jarContentsManager.findFiles(testFile, "test", "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFiles_nonExistingJarFile() {
        jarContentsManager.findFiles(new File("test"), "test", "test");
    }

    @Test
    public void findFiles_nonExistingFile() {
        List<String> result = jarContentsManager.findFiles(testJar, "blah", "nope");

        assertTrue("Expect to have empty results for non-existing file", result.isEmpty());
    }

    @Test
    public void findFiles_existingFiles() {
        String resourceName = "vaadin-charts-webjar-6.0.0-alpha3.jar";
        String searchName = "bower.json";

        List<String> bowerJsons = jarContentsManager.findFiles(
                TestUtils.getTestJar(resourceName), "", searchName);

        assertEquals(String.format("Expect '%s' WebJar to contain two '%s' files", resourceName, searchName), 2, bowerJsons.size());
        assertTrue(String.format("Expect all found paths to end with the file name searched for: '%s'", searchName),
                bowerJsons.stream().allMatch(path -> path.endsWith('/' + searchName)));
    }

    @Test
    public void findFiles_existingFiles_baseDirectoryMatters() {
        String resourceName = "vaadin-charts-webjar-6.0.0-alpha3.jar";
        String testPath = "META-INF/resources/webjars/highcharts/5.0.14/";
        String searchName = "bower.json";

        List<String> bowerJson = jarContentsManager.findFiles(
                TestUtils.getTestJar(resourceName), testPath, searchName);

        assertEquals(String.format("Expect '%s' WebJar to contain one '%s' file in directory '%s'", resourceName, searchName, testPath),
                1, bowerJson.size());
    }

    @Test(expected = NullPointerException.class)
    public void copyFilesFromJar_nullJarFile() {
        jarContentsManager.copyFilesFromJarTrimmingBasePath(null, null, testDirectory.getRoot());
    }

    @Test(expected = UncheckedIOException.class)
    public void copyFilesFromJar_notAJarFile() throws IOException {
        File testFile = testDirectory.newFile("test");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testFile, null, testDirectory.getRoot());
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyFilesFromJar_nonExistingJarFile() {
        jarContentsManager.copyFilesFromJarTrimmingBasePath(new File("test"), null, testDirectory.getRoot());
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyFilesFromJar_directoryInsteadOfJar() {
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testDirectory.getRoot(), null, testDirectory.getRoot());
    }

    @Test(expected = NullPointerException.class)
    public void copyFilesFromJar_nullOutputDirectory() {
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyFilesFromJar_fileInsteadOfDirectory() {
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, null, testJar);
    }

    @Test
    public void copyFilesFromJar_noBasePath_noExclusions() {
        assertThat("Do not expect any files in temporary directory before the test", TestUtils.listFilesRecursively(testDirectory.getRoot()).size(), is(0));

        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, null, testDirectory.getRoot());

        assertThat("Temporary directory should have files after jar copied", TestUtils.listFilesRecursively(testDirectory.getRoot()).size(), is(not(0)));
    }

    @Test
    public void copyFilesFromJar_noBasePath_excludeEverything() {
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, null, testDirectory.getRoot(), "*");
        assertThat("Do not expect any files with filter that excludes everything", TestUtils.listFilesRecursively(testDirectory.getRoot()).size(), is(0));
    }

    @Test
    public void copyFilesFromJar_withBasePath_noExclusions() throws IOException {
        String basePath = "META-INF/maven/";
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, basePath, testDirectory.getRoot());

        List<String> resultingPaths = TestUtils.listFilesRecursively(testDirectory.getRoot());
        assertThat(String.format("Expect jar '%s' to contain files with base path '%s'", testJar, basePath), resultingPaths.size(), is(not(0)));
        assertTrue("Resulting paths should not contain base path = " + basePath, resultingPaths.stream().noneMatch(path -> path.contains(basePath)));
    }

    @Test
    public void copyFilesFromJar_exclusionsWork() throws IOException {
        String basePath = "META-INF/maven";

        File notFilteredCopyingDirectory = testDirectory.newFolder("notFiltered");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, basePath, notFilteredCopyingDirectory);
        List<String> notFilteredPaths = TestUtils.listFilesRecursively(notFilteredCopyingDirectory);

        File filteredCopyingDirectory = testDirectory.newFolder("filtered");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, basePath, filteredCopyingDirectory, "*.xml");
        List<String> filteredPaths = TestUtils.listFilesRecursively(filteredCopyingDirectory);

        assertTrue("Filtered paths' count should be less than non filtered paths' count", filteredPaths.size() < notFilteredPaths.size());
        assertTrue("Not filtered paths should contain xml files", notFilteredPaths.stream().anyMatch(path -> path.endsWith(".xml")));
        assertTrue("Paths with '*.xml' exclusion should not contain xml files", filteredPaths.stream().noneMatch(path -> path.endsWith(".xml")));
    }

    @Test
    public void copyFilesFromJar_basePathAppendedWithTrailingSlash() throws IOException {
        String basePath1 = "META-INF/maven";
        File basePath1Directory = testDirectory.newFolder("basePath1");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, basePath1, basePath1Directory);
        List<String> basePath1Paths = TestUtils.listFilesRecursively(basePath1Directory);

        String basePath2 = basePath1 + '/';
        File basePath2Directory = testDirectory.newFolder("basePath2");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, basePath2, basePath2Directory);
        List<String> basePath2Paths = TestUtils.listFilesRecursively(basePath2Directory);

        assertEquals("Base path without trailing slash should be treated the same as base path with one", basePath1Paths, basePath2Paths);
    }

    @Test
    public void copyFilesFromJar_copiedFromBasePathResultsAreContainedInAllPaths() throws IOException {
        File allFilesDirectory = testDirectory.newFolder("all");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, null, allFilesDirectory);
        List<String> allPaths = TestUtils.listFilesRecursively(allFilesDirectory);

        String basePath = "/META-INF/maven";
        File filteredFilesDirectory = testDirectory.newFolder("filtered");
        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, basePath, filteredFilesDirectory);
        List<String> filteredPaths = TestUtils.listFilesRecursively(filteredFilesDirectory);
        List<String> filteredPathsPrefixedByBasePath = filteredPaths.stream().map(path -> basePath + path).collect(Collectors.toList());

        assertTrue("Filtered paths' count should be less than all paths' count", filteredPaths.size() < allPaths.size());
        assertTrue("base path + filtered path should be contained in all paths", allPaths.containsAll(filteredPathsPrefixedByBasePath));
    }

    @Test
    public void copyFilesFromJar_casePreserved() {
        File outputDirectory = testDirectory.getRoot();
        String jarDirectory = "META-INF/resources/webjars/paper-button/2.0.0/.github/";
        File testJar = TestUtils.getTestJar("paper-button-2.0.0.jar");
        List<String> originalFiles = listFilesInJar(testJar, jarDirectory);

        jarContentsManager.copyFilesFromJarTrimmingBasePath(testJar, jarDirectory, outputDirectory);

        Set<String> copiedFiles = new HashSet<>(TestUtils.listFilesRecursively(outputDirectory));

        assertEquals(String.format("Number of files in jar '%s' in jar directory '%s' and number of copied files should match.", testJar, jarDirectory),
                originalFiles.size(), copiedFiles.size());

        copiedFiles.forEach(copiedFile -> assertTrue(
                String.format("Failed to find copied file '%s' in files '%s' from jar '%s'", copiedFile, originalFiles, testJar),
                originalFiles.stream().anyMatch(file -> file.endsWith(copiedFile))));
    }

    private List<String> listFilesInJar(File jar, String jarDirectory) {
        try (JarFile jarFile = new JarFile(jar, false)) {
            return jarFile.stream()
                    .filter(file -> !file.isDirectory())
                    .filter(file -> file.getName().startsWith(jarDirectory))
                    .map(ZipEntry::getName)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new AssertionException(Formatter.of("Failed to list files in jarFile '%s'", jar), e);
        }
    }
}
