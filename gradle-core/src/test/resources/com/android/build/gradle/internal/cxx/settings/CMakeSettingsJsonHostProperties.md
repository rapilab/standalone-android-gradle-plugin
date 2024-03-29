This file generated by class com.android.build.gradle.internal.cxx.settings.CMakeSettingsJsonPropertiesGoldenFileUpdater

## ${projectDir}
Folder of the module level build.gradle file.
- example: $PROJECTS/MyProject/Source/Android/app1
- environment: microsoft-built-ins-from-android-ndk

## ${thisFile}
Path to this CMakeSettings.json file.
- example: $PROJECTS/MyProject/CMakeSettings.json
- environment: microsoft-built-ins-from-android-ndk

## ${thisFileDir}
Folder of this CMakeSettings.json file.
- example: $PROJECTS/MyProject
- environment: microsoft-built-ins-from-android-ndk

## ${workspaceRoot}
Folder of the project level build.gradle file.
- example: $PROJECTS/MyProject/Source/Android
- environment: microsoft-built-ins-from-android-ndk

## ${ndk.abi}
Currently targeted ABI.
- example: x86_64
- environment: android-gradle

## ${ndk.abiBitness}
The bitness of the targeted ABI.
- example: 64
- environment: android-ndk-abi-${ndk.abi}

## ${ndk.abiIs64Bits}
Whether the targeted ABI is 64-bits.
- example: 1
- environment: android-ndk-abi-${ndk.abi}

## ${ndk.abiIsDefault}
Whether the targeted ABI is a default ABI in the current Android NDK.
- example: 1
- environment: android-ndk-abi-${ndk.abi}

## ${ndk.abiIsDeprecated}
True if the targeted ABI is deprecated in the current Android NDK.
- example: 0
- environment: android-ndk-abi-${ndk.abi}

## ${ndk.androidGradleIsHosting}
True if Android Gradle Plugin is hosting this CMakeSettings.json.
- example: 1
- environment: ndk

## ${ndk.buildRoot}
The default CMake build root that gradle uses.
- example: ${ndk.moduleDir}/.cxx/cmake/debug/x86_64
- environment: android-gradle

## ${ndk.cFlags}
The value of cFlags from android.config.externalNativeBuild.cFlags in build.gradle.
- example: -DC_FLAG_DEFINED
- environment: android-gradle

## ${ndk.cmakeExecutable}
Path to CMake executable.
- example: ${ndk.sdkDir}/cmake/3.10.2/bin/cmake
- environment: android-ndk

## ${ndk.cmakeToolchain}
Path to the current Android NDK's CMake toolchain.
- example: ${ndk.dir}/build/cmake/android.toolchain.cmake
- environment: android-ndk

## ${ndk.configurationHash}
First eight characters of ${ndk.fullConfigurationHash}.
- example: 1m6w461r
- environment: android-gradle

## ${ndk.cppFlags}
The value of cppFlags from android.config.externalNativeBuild.cppFlags in build.gradle.
- example: -DCPP_FLAG_DEFINED
- environment: android-gradle

## ${ndk.defaultBuildType}
The CMAKE_BUILD_TYPE derived from the suffix of gradle variant name. May be Debug, Release, RelWithDebInfo, or MinSizeRel.
- example: Debug
- environment: android-gradle

## ${ndk.defaultLibraryOutputDirectory}
The default CMake CMAKE_LIBRARY_OUTPUT_DIRECTORY that gradle uses.
- example: ${ndk.moduleDir}/build/intermediates/cmake/debug/obj/x86_64
- environment: android-gradle

## ${ndk.dir}
Folder of the current Android NDK.
- example: ${ndk.sdkDir}/ndk/21.0.6113669
- environment: android-ndk

## ${ndk.fullConfigurationHash}
Hash of this CMakeSettings configuration.
- example: 1m6w461rf3l272y5d5d5c2m651a4i4j1c3n69zm476ys1g403j69363k4519
- environment: android-gradle

## ${ndk.maxPlatform}
The maximum Android platform supported by the current Android NDK.
- example: 29
- environment: android-ndk

## ${ndk.minPlatform}
The minimum Android platform supported by the current Android NDK.
- example: 16
- environment: android-ndk

## ${ndk.moduleDir}
Folder of the module level build.gradle.
- example: $PROJECTS/MyProject/Source/Android/app1
- environment: android-gradle

## ${ndk.moduleName}
Name of the gradle module.
- example: app1
- environment: android-gradle

## ${ndk.ninjaExecutable}
Path to Ninja executable if one was found by Gradle. Otherwise, it expands to empty string and it's up to CMake to find the ninja executable.
- example: ${ndk.sdkDir}/cmake/3.10.2/bin/ninja
- environment: android-ndk

## ${ndk.platform}
The currently targeted Android platform string, that can be passed to CMake in ANDROID_PLATFORM.
- example: android-19
- environment: android-ndk-platform-${ndk.systemVersion}

## ${ndk.platformCode}
The currently targeted Android platform code name.
- example: K
- environment: android-ndk-platform-${ndk.systemVersion}

## ${ndk.prefabPath}
The CMAKE_FIND_ROOT_PATH to be used by Prefab for the current configuration.
- example: $PROJECTS/MyProject/Source/Android/app1/.cxx/cmake/debug/prefab/x86_64/prefab
- environment: android-gradle

## ${ndk.projectDir}
Folder of the gradle root project build.gradle.
- example: $PROJECTS/MyProject/Source/Android
- environment: android-gradle

## ${ndk.sdkDir}
Folder of the current Android SDK.
- example: $HOME/Library/Android/sdk
- environment: android-gradle

## ${ndk.systemVersion}
The currently targeted Android system version, suitable for passing to CMake in CMAKE_SYSTEM_VERSION.
- example: 19
- environment: android-ndk-platform-${ndk.systemVersion}

## ${ndk.variantName}
Name of the gradle variant.
- example: debug
- environment: android-gradle

## ${ndk.version}
Version of NDK.
- example: 21.0.6113669
- environment: android-ndk

## ${ndk.versionMajor}
Version number major part.
- example: 21
- environment: android-ndk

## ${ndk.versionMinor}
Version number minor part.
- example: 0
- environment: android-ndk

