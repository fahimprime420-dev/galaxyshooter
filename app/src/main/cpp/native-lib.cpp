#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_galaxyshooter_physics_Collision_nativeVersion(JNIEnv* env, jobject) {
    return env->NewStringUTF("Galaxy NDK 1.0");
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_galaxyshooter_physics_Collision_circlesOverlap(
        JNIEnv*, jobject,
        jfloat x1, jfloat y1, jfloat r1,
        jfloat x2, jfloat y2, jfloat r2) {
    float dx = x1 - x2, dy = y1 - y2, r = r1 + r2;
    return (dx*dx + dy*dy) <= r*r ? JNI_TRUE : JNI_FALSE;
}
