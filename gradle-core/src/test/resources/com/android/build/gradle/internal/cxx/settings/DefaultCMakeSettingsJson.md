This file is generated by class com.android.build.gradle.internal.cxx.settings.CMakeSettingsDefaultConfigurationGoldenFileTest

This is the default configuration that will be used when the user has not specified a CMakeSettings configuration.
Any changes here will affect most C/C++ users.

```
{
  "environments": [],
  "configurations": [
    {
      "name": "traditional-android-studio-cmake-environment",
      "description": "Configuration generated by Android Gradle Plugin",
      "generator": "Ninja",
      "configurationType": "${ndk.defaultBuildType}",
      "inheritEnvironments": [
        "ndk"
      ],
      "buildRoot": "${ndk.buildRoot}",
      "cmakeToolchain": "${ndk.cmakeToolchain}",
      "cmakeExecutable": "${ndk.cmakeExecutable}",
      "variables": [
        {
          "name": "ANDROID_ABI",
          "value": "${ndk.abi}"
        },
        {
          "name": "ANDROID_NDK",
          "value": "${ndk.dir}"
        },
        {
          "name": "ANDROID_PLATFORM",
          "value": "${ndk.platform}"
        },
        {
          "name": "CMAKE_ANDROID_ARCH_ABI",
          "value": "${ndk.abi}"
        },
        {
          "name": "CMAKE_ANDROID_NDK",
          "value": "${ndk.dir}"
        },
        {
          "name": "CMAKE_C_FLAGS",
          "value": "${ndk.cFlags}"
        },
        {
          "name": "CMAKE_CXX_FLAGS",
          "value": "${ndk.cppFlags}"
        },
        {
          "name": "CMAKE_EXPORT_COMPILE_COMMANDS",
          "value": "ON"
        },
        {
          "name": "CMAKE_LIBRARY_OUTPUT_DIRECTORY",
          "value": "${ndk.defaultLibraryOutputDirectory}"
        },
        {
          "name": "CMAKE_MAKE_PROGRAM",
          "value": "${ndk.ninjaExecutable}"
        },
        {
          "name": "CMAKE_SYSTEM_NAME",
          "value": "Android"
        },
        {
          "name": "CMAKE_SYSTEM_VERSION",
          "value": "${ndk.systemVersion}"
        }
      ]
    }
  ]
}
```
