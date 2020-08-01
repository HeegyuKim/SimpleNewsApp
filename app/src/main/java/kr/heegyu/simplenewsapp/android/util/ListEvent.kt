package kr.heegyu.simplenewsapp.android.util


sealed class ListEvent<T>
{
    class InsertEvent<T>(val items: Collection<T>): ListEvent<T>()
    class UpdateEvent<T>(val items: Collection<T>): ListEvent<T>()
    class DeleteEvent<T>(val items: Collection<T>): ListEvent<T>()
    class RefreshEvent<T>: ListEvent<T>()

    companion object {
        @JvmStatic
        val INDEX_LAST = -1
    }
}