package com.softawii.ticketbot.util

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

class ReflectionUtil {
    companion object {
        fun getClassesByPackageName(pkgName: String): Set<Class<out Any>> {
            val reflections = Reflections(
                ConfigurationBuilder()
                    .filterInputsBy(FilterBuilder().includePackage(pkgName))
                    .setUrls(ClasspathHelper.forPackage(pkgName))
                    .setScanners(Scanners.SubTypes.filterResultsBy { true })
            )

            return reflections.getSubTypesOf(Object::class.java)
        }
    }
}