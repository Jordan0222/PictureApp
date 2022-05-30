package com.jordan.pictureapp.data.paging

interface Paginator<Key, Item> {
    suspend fun loadNextItem()
    fun reset()
}