package io.fhate.core.data.wrapper

/* A generic sealed class that contains data and status about getting this data */
sealed class LoadableData<T>(
    val data: T? = null,
    val errorCode: Int = 0,
    val errorMessage: String? = null
) {
    class Success<T>(data: T): LoadableData<T>(data)
    class Loading<T>(data: T? = null): LoadableData<T>(data)
    class Error<T>(errorCode: Int, errorMessage: String? = null)
        :LoadableData<T>(null, errorCode, errorMessage)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success [data = $data]"
            is Error -> "Error [exception = $errorCode]"
            is Loading<T> -> "Loading"
        }
    }


}
