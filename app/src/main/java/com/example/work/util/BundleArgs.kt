package com.example.work.util


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance

interface BundleArgs : Serializable {

    fun toBundle(): Bundle {
        val argsKey = getClassName(this::class)

        return when (this) {
            is Parcelable -> bundleOf(argsKey to this)
            else -> bundleOf(argsKey to toByteArray())
        }.apply {
            classLoader = this@BundleArgs::class.java.classLoader
        }
    }

    private fun Any.toByteArray(): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        ObjectOutputStream(byteArrayOutputStream)
            .use {
                it.writeObject(this)
                it.flush()
            }

        return byteArrayOutputStream.toByteArray()
    }
}

class ArgsCreator<ARGS : BundleArgs>(
    private val kClass: KClass<ARGS>,
    private val defaultArgs: (() -> ARGS)? = null
) {

    companion object {

        inline operator fun <reified ARGS : BundleArgs> invoke(noinline defaultArgs: (() -> ARGS)? = null): ArgsCreator<ARGS> {
            return ArgsCreator(ARGS::class, defaultArgs)
        }
    }

    private var value: ARGS? = null

    operator fun getValue(activity: Activity, property: KProperty<*>): ARGS {
        return try {
            value ?: createArgs(activity).apply { value = this }
        } catch (e: Exception) {
            createInvalidArgs(e)
        }
    }

    operator fun getValue(fragment: Fragment, property: KProperty<*>): ARGS {
        return try {
            return value ?: createArgs(fragment).apply { value = this }
        } catch (e: Exception) {
            createInvalidArgs(e)
        }
    }

    operator fun setValue(activity: Activity, property: KProperty<*>, args: ARGS?) {
        value = args
    }

    operator fun setValue(fragment: Fragment, property: KProperty<*>, args: ARGS?) {
        value = args
    }

    fun createArgs(intent: Intent): ARGS {
        return fromBundle(intent.extras)
    }

    fun createArgs(bundle: Bundle): ARGS {
        return fromBundle(bundle)
    }

    private fun createArgs(activity: Activity): ARGS {
        return fromBundle(activity.intent.extras)
    }

    private fun createArgs(fragment: Fragment): ARGS {
        return fromBundle(fragment.arguments)
    }

    @Suppress("UNCHECKED_CAST")
    private fun fromBundle(bundle: Bundle?): ARGS {
        return try {
            if (null == bundle) {
                throw IllegalArgumentException("bundle should not be empty")
            }

            val argsKey = getClassName(kClass)

            if (Parcelable::class.java.isAssignableFrom(kClass.java)) {
                bundle.getNonNullParcelable<Parcelable>(argsKey) as ARGS
            } else {
                bundle.getNonNullByteArray(argsKey).toObject()
            }
        } catch (e: Exception) {
            defaultArgs?.invoke() ?: throw e
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> ByteArray.toObject(): T {
        val byteArrayInputStream = ByteArrayInputStream(this)

        return ObjectInputStream(byteArrayInputStream)
            .use {
                it.readObject() as T
            }
    }

    private fun createInvalidArgs(sourceError: Exception): ARGS {
        return try {
            kClass.createInstance()
        } catch (e: IllegalArgumentException) {
            throw sourceError
        }
    }
}

private fun getClassName(kClass: KClass<*>): String = kClass.java.name

fun Intent.putArgs(args: BundleArgs) {
    putExtras(args.toBundle())
}

fun Fragment.putArgs(args: BundleArgs) {
    with(arguments) {
        if (null == this) {
            arguments = args.toBundle()
        } else {
            putArgs(args)
        }
    }
}

fun Bundle.putArgs(args: BundleArgs) {
    putAll(args.toBundle())
}

fun <T : Parcelable> Bundle.getNonNullParcelable(key: String): T {
    return getParcelable(key)
        ?: throw IllegalArgumentException("There is no data in the Bundle that can be found by the $key.")
}

fun Bundle.getNonNullByteArray(key: String): ByteArray {
    return getByteArray(key)
        ?: throw IllegalArgumentException("There is no data in the Bundle that can be found by the $key.")
}
