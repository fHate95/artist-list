package io.fhate.core.ui.adapter

/* Implementation for items should be used with GenericAdapter */
abstract class ListItemViewModel {
    var adapterPosition: Int = -1
    open var viewType: Int = 0
    var clickListener: GenericAdapter.ClickListener? = null
}