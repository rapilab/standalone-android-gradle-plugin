/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.build.gradle.internal.tasks

import com.android.build.gradle.internal.core.VariantDslInfo
import com.android.build.gradle.internal.scope.VariantScope
import com.android.ide.common.workers.WorkerExecutorFacade
import com.google.common.reflect.ClassPath
import com.google.common.reflect.TypeToken
import com.google.common.truth.Truth
import org.gradle.api.Task
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.CompileClasspath
import org.gradle.api.tasks.Console
import org.gradle.api.tasks.Destroys
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.LocalState
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.SkipWhenEmpty
import org.junit.Test
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.function.Supplier

class TaskMethodModifiersAndAnnotationsTest {

    @Test
    fun `check for non-public methods with gradle input or output annotations`() {
        val classPath = ClassPath.from(this.javaClass.classLoader)
        val nonPublicMethods =
            classPath
                .getTopLevelClassesRecursive("com.android.build")
                .map { classInfo -> classInfo.load() as Class<*> }
                .flatMap { it.declaredMethods.asIterable() }
                .filter {
                    it.hasGradleInputOrOutputAnnotation() && !Modifier.isPublic(it.modifiers)
                }

        if (nonPublicMethods.isEmpty()) {
            return
        }

        // Otherwise generate a descriptive error message.
        val error =
            StringBuilder().append("The following gradle-annotated methods are not public:\n")
        for (nonPublicMethod in nonPublicMethods) {
            error.append(nonPublicMethod.declaringClass.toString().substringAfter(" "))
                .append("::${nonPublicMethod.name}\n")
        }
        throw AssertionError(error.toString())
    }

    @Test
    fun `check for fields with gradle input or output annotations`() {
        val classPath = ClassPath.from(this.javaClass.classLoader)
        val annotatedFields =
            classPath
                .getTopLevelClassesRecursive("com.android.build")
                .map { classInfo -> classInfo.load() as Class<*> }
                .flatMap { it.declaredFields.asIterable() }
                .filter { it.hasGradleInputOrOutputAnnotation() }

        if (annotatedFields.isEmpty()) {
            return
        }

        // Otherwise generate a descriptive error message.
        val error =
            StringBuilder().append(
                "The following fields are annotated with gradle input/output annotations, which "
                        + "should only be used on methods (e.g., the corresponding getters):\n")
        for (annotatedField in annotatedFields) {
            error.append(annotatedField.declaringClass.toString().substringAfter(" "))
                .append(".${annotatedField.name}\n")
        }
        throw AssertionError(error.toString())
    }

    @Test
    fun `check for public task setters`() {

        val whiteListedSetters =
            listOf(
                "com.android.build.gradle.internal.coverage.JacocoReportTask::setCoverageFile",
                "com.android.build.gradle.internal.coverage.JacocoReportTask::setJacocoClasspath",
                "com.android.build.gradle.internal.coverage.JacocoReportTask::setReportDir",
                "com.android.build.gradle.internal.coverage.JacocoReportTask::setReportName",
                "com.android.build.gradle.internal.coverage.JacocoReportTask::setTabWidth",
                "com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask::setMergeBlameLogFolder",
                "com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask::setType",
                "com.android.build.gradle.internal.tasks.AndroidReportTask::setIgnoreFailures",
                "com.android.build.gradle.internal.tasks.AndroidReportTask::setReportType",
                "com.android.build.gradle.internal.tasks.AndroidReportTask::setWillRun",
                "com.android.build.gradle.internal.tasks.AndroidVariantTask::setVariantName",
                "com.android.build.gradle.internal.tasks.DependencyReportTask::setVariants",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setCoverageDir",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setDeviceProvider",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setFlavorName",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setIgnoreFailures",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setInstallOptions",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setReportsDir",
                "com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask::setTestData",
                "com.android.build.gradle.internal.tasks.IncrementalTask::setIncrementalFolder",
                "com.android.build.gradle.internal.tasks.InstallVariantTask::setInstallOptions",
                "com.android.build.gradle.internal.tasks.InstallVariantTask::setTimeOutInMs",
                "com.android.build.gradle.internal.tasks.InstallVariantTask::setVariantData",
                "com.android.build.gradle.internal.tasks.LintCompile::setOutputDirectory",
                "com.android.build.gradle.internal.tasks.LintStandaloneTask::setAutoFix",
                "com.android.build.gradle.internal.tasks.LintStandaloneTask::setFatalOnly",
                "com.android.build.gradle.internal.tasks.LintStandaloneTask::setLintChecks",
                "com.android.build.gradle.internal.tasks.LintStandaloneTask::setLintOptions",
                "com.android.build.gradle.internal.tasks.LintStandaloneTask::setReportDir",
                "com.android.build.gradle.internal.tasks.MergeFileTask::setInputFiles",
                "com.android.build.gradle.internal.tasks.NdkTask::setNdkConfig",
                "com.android.build.gradle.internal.tasks.PackageRenderscriptTask::setVariantName",
                "com.android.build.gradle.internal.tasks.ProcessJavaResTask::setVariantName",
                "com.android.build.gradle.internal.tasks.SigningReportTask::setVariants",
                "com.android.build.gradle.internal.tasks.SourceSetsTask::setExtension",
                "com.android.build.gradle.internal.tasks.TestServerTask::setTestServer",
                "com.android.build.gradle.internal.tasks.UninstallTask::setTimeOutInMs",
                "com.android.build.gradle.internal.tasks.UninstallTask::setVariant",
                "com.android.build.gradle.tasks.factory.AndroidUnitTest::setVariantName",
                "com.android.build.gradle.tasks.AidlCompile::setPackageWhitelist",
                "com.android.build.gradle.tasks.BundleAar::setVariantName",
                "com.android.build.gradle.tasks.CleanBuildCache::setBuildCache",
                "com.android.build.gradle.tasks.ExtractAnnotations::setBootClasspath",
                "com.android.build.gradle.tasks.ExtractAnnotations::setClassDir",
                "com.android.build.gradle.tasks.ExtractAnnotations::setEncoding",
                "com.android.build.gradle.tasks.GenerateBuildConfig::setBuildTypeName",
                "com.android.build.gradle.tasks.GenerateBuildConfig::setSourceOutputDir",
                "com.android.build.gradle.tasks.GenerateResValues::setResOutputDir",
                "com.android.build.gradle.tasks.InvokeManifestMerger::setMainManifestFile",
                "com.android.build.gradle.tasks.InvokeManifestMerger::setOutputFile",
                "com.android.build.gradle.tasks.InvokeManifestMerger::setSecondaryManifestFiles",
                "com.android.build.gradle.tasks.LintPerVariantTask::setVariantName",
                "com.android.build.gradle.tasks.MergeResources::setBlameLogFolder",
                "com.android.build.gradle.tasks.PackageAndroidArtifact::setAbiFilters",
                "com.android.build.gradle.tasks.PackageAndroidArtifact::setJniDebugBuild",
                "com.android.build.gradle.tasks.PackageAndroidArtifact::setSigningConfig",
                "com.android.build.gradle.tasks.ProcessTestManifest::setTmpDir",
                "com.android.build.gradle.tasks.RenderscriptCompile::setImportDirs",
                "com.android.build.gradle.tasks.RenderscriptCompile::setNdkMode",
                "com.android.build.gradle.tasks.RenderscriptCompile::setObjOutputDir",
                "com.android.build.gradle.tasks.RenderscriptCompile::setOptimLevel",
                "com.android.build.gradle.tasks.RenderscriptCompile::setResOutputDir",
                "com.android.build.gradle.tasks.RenderscriptCompile::setSupportMode",
                "com.android.build.gradle.tasks.ShaderCompile::setDefaultArgs",
                "com.android.build.gradle.tasks.ShaderCompile::setScopedArgs"
            )

        val classPath = ClassPath.from(this.javaClass.classLoader)
        val taskInterface = TypeToken.of(Task::class.java)
        val publicSetters =
            classPath
                .getTopLevelClassesRecursive("com.android.build")
                .map { classInfo -> classInfo.load() as Class<*> }
                .filter { clazz -> TypeToken.of(clazz).types.contains(taskInterface) }
                .flatMap { it.declaredMethods.asIterable() }
                .filter {
                    it.name.startsWith("set")
                            && !it.name.contains('$')
                            && Modifier.isPublic(it.modifiers)
                }
                .map { "${it.declaringClass.toString().substringAfter(" ")}::${it.name}" }

        Truth.assertThat(publicSetters)
            .named("Task public setters")
            .containsExactlyElementsIn(whiteListedSetters)
    }

    @Test
    fun checkWorkerFacadeIsNotAField() {
        Truth.assertThat(findTaskFieldsOfType(WorkerExecutorFacade::class.java)).isEmpty()
    }

    @Test
    fun checkVariantScopeIsNotAField() {
        Truth.assertThat(findTaskFieldsOfType(VariantScope::class.java)).isEmpty()
    }

    @Test
    fun checkVariantConfigurationIsNotAField() {
        Truth.assertThat(findTaskFieldsOfType(VariantDslInfo::class.java)).isEmpty()
    }

    @Test
    fun checkSupplierIsNotAField() {
        Truth.assertThat(findTaskFieldsOfType(Supplier::class.java)).isEmpty()
    }

    private fun findTaskFieldsOfType(ofType: Class<*>): List<Field> {
        val classPath = ClassPath.from(this.javaClass.classLoader)
        val taskInterface = TypeToken.of(Task::class.java)
        val fieldType = TypeToken.of(ofType)
        return classPath
            .getTopLevelClassesRecursive("com.android.build")
            .map { classInfo -> classInfo.load() as Class<*> }
            .filter { clazz -> TypeToken.of(clazz).types.contains(taskInterface) }
            .flatMap { it.declaredFields.asIterable() }
            .filter { TypeToken.of(it.type).isSubtypeOf(fieldType) }
    }

    private fun AnnotatedElement.hasGradleInputOrOutputAnnotation(): Boolean {
        // look for all org.gradle.api.tasks annotations, except @CacheableTask, @Internal, and
        // @TaskAction.
        return getAnnotation(Classpath::class.java) != null
                || getAnnotation(CompileClasspath::class.java) != null
                || getAnnotation(Console::class.java) != null
                || getAnnotation(Destroys::class.java) != null
                || getAnnotation(Input::class.java) != null
                || getAnnotation(InputDirectory::class.java) != null
                || getAnnotation(InputFile::class.java) != null
                || getAnnotation(InputFiles::class.java) != null
                || getAnnotation(LocalState::class.java) != null
                || getAnnotation(Nested::class.java) != null
                || getAnnotation(Optional::class.java) != null
                || getAnnotation(OutputDirectories::class.java) != null
                || getAnnotation(OutputDirectory::class.java) != null
                || getAnnotation(OutputFile::class.java) != null
                || getAnnotation(OutputFiles::class.java) != null
                || getAnnotation(PathSensitive::class.java) != null
                || getAnnotation(SkipWhenEmpty::class.java) != null
    }
}
