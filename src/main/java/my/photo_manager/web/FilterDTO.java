package my.photo_manager.web;

import my.photo_manager.filter.FilterCategory;

public record FilterDTO(FilterCategory category, String text) {
}
