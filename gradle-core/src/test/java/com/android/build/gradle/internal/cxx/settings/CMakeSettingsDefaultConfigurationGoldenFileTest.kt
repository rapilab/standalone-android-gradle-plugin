/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.android.build.gradle.internal.cxx.settings

import com.android.build.gradle.internal.cxx.model.BasicCmakeMock
import com.android.testutils.GoldenFile
import org.junit.Test

/**
 * Print the default CMakeSettings.json configuration. This is the configuration that will be used
 * when the user hasn't specified a configuration name in build.gradle.
 *
 * The expected result file can be updated by running [CMakeSettingsDefaultConfigurationGoldenFileUpdater.main]
 */
class CMakeSettingsDefaultConfigurationGoldenFileTest {

    @Test
    fun validate() {
        goldenFile.assertUpToDate()
    }

    companion object {
        internal val goldenFile = GoldenFile(
            resourceRootWorkspacePath = "tools/base/build-system/gradle-core/src/test/resources",
            resourcePath = "com/android/build/gradle/internal/cxx/settings/DefaultCMakeSettingsJson.md",
            actualCallable = {
                val result = mutableListOf<String>()
                result += "This file is generated by ${CMakeSettingsDefaultConfigurationGoldenFileTest::class.java}"
                result += ""
                result += "This is the default configuration that will be used when the user has not specified a CMakeSettings configuration."
                result += "Any changes here will affect most C/C++ users."
                result += ""
                result += "```"
                BasicCmakeMock().let {
                    result += it.abi.getCmakeServerDefaultEnvironment().toJsonString().lines()
                }
                result += "```"
                result
            })
    }
}
