package com.kyi.openfoodfactsapi.utils

import ImageField
import com.kyi.openfoodfactsapi.models.Product

object ProductHelper {

    fun removeImages(product: Product, language: OpenFoodFactsLanguage?) {
        product.selectedImages?.let { images ->
            for (field in ImageField.entries) {
                if (images.any { it.field == field && it.language == language }) {
                    images.removeAll { it.field == field && it.language != language }
                }
            }
        }
    }

    fun createImageUrls(product: Product, uriHelper: UriProductHelper) {
        product.images?.let { images ->
            product.barcode?.let { barcode ->
                for (image in images) {
                    image.url = image.getUrl(barcode, uriHelper = uriHelper)
                }
            }
        }
    }
}