# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Preserve line number information for debugging stack traces sent to Crashlytics.
-keepattributes SourceFile,LineNumberTable

# Hide the original source file name in stack traces.
-renamesourcefileattribute SourceFile

#Firebase
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep public class * extends java.lang.Exception


