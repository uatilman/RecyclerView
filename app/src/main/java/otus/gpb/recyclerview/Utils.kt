package otus.gpb.recyclerview

fun <T> Any.asClass(): T? = if (this as? T == null) null else this