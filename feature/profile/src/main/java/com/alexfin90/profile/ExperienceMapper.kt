package com.alexfin90.profile


import com.alexfin90.domain.entities.Experience
import com.alexfin90.profile.uiModel.UiProfileItem


fun Experience.toUiModel() = UiProfileItem(
    title = this.title,
    companyName = this.company
)