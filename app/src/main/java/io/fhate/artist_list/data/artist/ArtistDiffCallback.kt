package io.fhate.artist_list.data.artist

import androidx.recyclerview.widget.DiffUtil

class ArtistDiffCallback(
    private val oldList: List<ArtistModel>,
    private val newList: List<ArtistModel>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.name == newItem.name
                && oldItem.listeners == newItem.listeners
                && oldItem.images == oldItem.images
    }
}