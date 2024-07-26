package com.testtask.ideaplatform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val userRepository: ItemRepositoryImpl) : ViewModel() {
    private val _allItems = MutableStateFlow<List<Item>>(emptyList())
    val allItems: StateFlow<List<Item>> get() = _allItems

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    init {
        viewModelScope.launch {
            collectAllItems()
        }
    }

    private suspend fun collectAllItems() {
        userRepository.getAllItems().collect { items ->
            _allItems.value = items
        }
    }

    private suspend fun collectFilteredItems() {
        val query = _searchQuery.value
        userRepository.findItemsByName("%$query%").collect { items ->
            _allItems.value = items
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            userRepository.updateItem(item)
            applySearchQuery()
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            userRepository.deleteItem(item)
            applySearchQuery()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            applySearchQuery()
        }
    }
    private suspend fun applySearchQuery() {
        val query = _searchQuery.value
        if (query.isBlank()) {
            collectAllItems()
        } else {
            collectFilteredItems()
        }
    }
}