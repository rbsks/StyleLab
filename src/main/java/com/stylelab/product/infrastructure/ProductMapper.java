package com.stylelab.product.infrastructure;

import com.stylelab.product.domain.Product;
import com.stylelab.product.domain.ProductImage;
import com.stylelab.product.domain.ProductOption1;
import com.stylelab.product.domain.ProductOption2;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity mapProductToEntity(Product product) {
        return ProductEntity.builder()
                .productId(product.productId())
                .storeId(product.storeId())
                .productCategoryPath(product.productCategoryPath())
                .name(product.name())
                .price(product.price())
                .discountPrice(product.discountPrice())
                .discountRate(product.discountRate())
                .useOption(product.useOption())
                .optionDepth(product.optionDepth())
                .option1(product.option1())
                .option2(product.option2())
                .quantity(product.quantity())
                .soldOut(product.soldOut())
                .deleted(product.deleted())
                .build();
    }

    public Product mapProductEntityToDomain(ProductEntity productEntity) {
        return Product.builder()
                .productId(productEntity.getProductId())
                .storeId(productEntity.getStoreId())
                .productCategoryPath(productEntity.getProductCategoryPath())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .discountPrice(productEntity.getDiscountPrice())
                .discountRate(productEntity.getDiscountRate())
                .useOption(productEntity.isUseOption())
                .optionDepth(productEntity.getOptionDepth())
                .option1(productEntity.getOption1())
                .option2(productEntity.getOption2())
                .quantity(productEntity.getQuantity())
                .soldOut(productEntity.isSoldOut())
                .deleted(productEntity.isDeleted())
                .build();
    }

    public ProductImageEntity mapProductImageToEntity(ProductImage productImage) {
        return ProductImageEntity.builder()
                .productImageId(productImage.productImageId())
                .productId(productImage.productId())
                .imageUrl(productImage.imageUrl())
                .imageOrder(productImage.imageOrder())
                .imageType(productImage.imageType())
                .build();
    }

    public ProductImage mapProductImageEntityToDomain(ProductImageEntity productImageEntity) {
        return ProductImage.builder()
                .productImageId(productImageEntity.getProductImageId())
                .productId(productImageEntity.getProductId())
                .imageUrl(productImageEntity.getImageUrl())
                .imageOrder(productImageEntity.getImageOrder())
                .imageType(productImageEntity.getImageType())
                .build();
    }

    public ProductOption1Entity mapProductOption1ToEntity(ProductOption1 productOption1) {
        return ProductOption1Entity.builder()
                .productOption1Id(productOption1.productOption1Id())
                .productId(productOption1.productId())
                .option1Name(productOption1.option1Name())
                .quantity(productOption1.quantity())
                .additionalPrice(productOption1.additionalPrice())
                .soldOut(productOption1.soldOut())
                .deleted(productOption1.deleted())
                .build();
    }

    public ProductOption1 mapProductOption1EntityToDomain(ProductOption1Entity productOption1Entity) {
        return ProductOption1.builder()
                .productOption1Id(productOption1Entity.getProductOption1Id())
                .productId(productOption1Entity.getProductId())
                .option1Name(productOption1Entity.getOption1Name())
                .quantity(productOption1Entity.getQuantity())
                .additionalPrice(productOption1Entity.getAdditionalPrice())
                .soldOut(productOption1Entity.isSoldOut())
                .deleted(productOption1Entity.isDeleted())
                .build();
    }

    public ProductOption2Entity mapProductOption2ToEntity(ProductOption2 productOption2) {
        return ProductOption2Entity.builder()
                .productOption2Id(productOption2.productOption2Id())
                .productOption1Id(productOption2.productOption1Id())
                .option2Name(productOption2.option2Name())
                .quantity(productOption2.quantity())
                .additionalPrice(productOption2.additionalPrice())
                .soldOut(productOption2.soldOut())
                .deleted(productOption2.deleted())
                .build();
    }

    public ProductOption2 mapProductOption2EntityToDomain(ProductOption2Entity productOption2Entity) {
        return ProductOption2.builder()
                .productOption2Id(productOption2Entity.getProductOption2Id())
                .productOption1Id(productOption2Entity.getProductOption1Id())
                .option2Name(productOption2Entity.getOption2Name())
                .quantity(productOption2Entity.getQuantity())
                .additionalPrice(productOption2Entity.getAdditionalPrice())
                .soldOut(productOption2Entity.isSoldOut())
                .deleted(productOption2Entity.isDeleted())
                .build();
    }
}
