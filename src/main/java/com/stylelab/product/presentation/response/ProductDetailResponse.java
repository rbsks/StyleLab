package com.stylelab.product.presentation.response;

import com.stylelab.product.infrastructure.dto.ProductDetail;
import com.stylelab.product.infrastructure.dto.ProductDetailImage;
import com.stylelab.storage.constant.ImageType;

import java.util.List;

public record ProductDetailResponse(
        Product product,
        Images images
) {

    public record Product(
            Long productId,
            Long storeId,
            String storeName,
            String storeBrand,
            String categoryPath,
            String categoryName,
            String productName,
            int price,
            int discountPrice,
            int discountRate,
            boolean useOption,
            int optionDepth,
            String option1,
            String option2,
            int quantity,
            boolean soldOut,
            boolean deleted
    ) {

        private static Product createProduct(ProductDetail productDetail) {
            return new Product(
                    productDetail.productId() , productDetail.storeId(), productDetail.storeName(), productDetail.storeBrand(),
                    productDetail.categoryPath(), productDetail.categoryName(), productDetail.productName(), productDetail.price(),
                    productDetail.discountPrice(), productDetail.discountRate(), productDetail.useOption(), productDetail.optionDepth(),
                    productDetail.option1(), productDetail.option2(), productDetail.quantity(), productDetail.soldOut(), productDetail.deleted()
            );
        }

    }

    public record Images(
            List<EntryMain> entryMain,
            List<EntrySub> entrySubs,
            List<Description> descriptions
    ) {
        public record EntryMain(
                Long productImageId,
                Long productId,
                String imageUrl,
                int imageOrder,
                ImageType imageType
        ) {

            private static EntryMain createEntryMain(ProductDetailImage productDetailImage) {
                return new EntryMain(
                        productDetailImage.productImageId(),
                        productDetailImage.productId(),
                        productDetailImage.imageUrl(),
                        productDetailImage.imageOrder(),
                        productDetailImage.imageType()
                );
            }
        }

        public record EntrySub(
                Long productImageId,
                Long productId,
                String imageUrl,
                int imageOrder,
                ImageType imageType
        ) {

            private static EntrySub createEntrySub(ProductDetailImage productDetailImage) {
                return new EntrySub(
                        productDetailImage.productImageId(),
                        productDetailImage.productId(),
                        productDetailImage.imageUrl(),
                        productDetailImage.imageOrder(),
                        productDetailImage.imageType()
                );
            }
        }

        public record Description(
                Long productImageId,
                Long productId,
                String imageUrl,
                int imageOrder,
                ImageType imageType
        ) {

            private static Description createDescription(ProductDetailImage productDetailImage) {
                return new Description(
                        productDetailImage.productImageId(),
                        productDetailImage.productId(),
                        productDetailImage.imageUrl(),
                        productDetailImage.imageOrder(),
                        productDetailImage.imageType()
                );
            }
        }
        private static Images createImages(List<ProductDetailImage> productDetailImages) {
            return new Images(
                    productDetailImages.stream()
                            .filter(productDetailImage -> productDetailImage.imageType() == ImageType.PRODUCT_ENTRY_MAIN)
                            .map(EntryMain::createEntryMain)
                            .toList(),
                    productDetailImages.stream()
                            .filter(productDetailImage -> productDetailImage.imageType() == ImageType.PRODUCT_ENTRY_SUB)
                            .map(EntrySub::createEntrySub)
                            .toList(),
                    productDetailImages.stream()
                            .filter(productDetailImage -> productDetailImage.imageType() == ImageType.PRODUCT_DESCRIPTION)
                            .map(Description::createDescription)
                            .toList()
            );
        }
    }

    public static ProductDetailResponse create(ProductDetail productDetail, List<ProductDetailImage> productDetailImage) {

        return new ProductDetailResponse(
                Product.createProduct(productDetail),
                Images.createImages(productDetailImage)
        );
    }
}
