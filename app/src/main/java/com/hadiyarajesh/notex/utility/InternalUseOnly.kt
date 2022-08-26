package com.hadiyarajesh.notex.utility

/**
 * This annotation specify that given class/function is for internal use only
 * and is not meant for public api.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class InternalUseOnly
