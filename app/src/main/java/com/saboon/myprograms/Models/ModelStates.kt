package com.saboon.myprograms.Models

import androidx.room.Entity

data class ModelStates(
    var loading: Boolean,
    var empty: Boolean,
    var error: Boolean,
)
