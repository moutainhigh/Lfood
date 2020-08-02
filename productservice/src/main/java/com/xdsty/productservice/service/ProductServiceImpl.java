package com.xdsty.productservice.service;

import basecommon.exception.BusinessRuntimeException;
import basecommon.util.PageUtil;
import com.google.common.collect.Lists;
import com.xdsty.productclient.dto.ProductIdDto;
import com.xdsty.productclient.dto.ProductQueryDto;
import com.xdsty.productclient.dto.ProductValidDto;
import com.xdsty.productclient.re.*;
import com.xdsty.productclient.service.ProductService;
import com.xdsty.productservice.entity.AdditionalItem;
import com.xdsty.productservice.entity.Product;
import com.xdsty.productservice.entity.ProductListQuery;
import com.xdsty.productservice.mapper.ProductMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/6/3 14:16
 */
@Service
@DubboService(timeout = 3000, version = "1.0")
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductMapper productMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ProductDetailRe getProductById(ProductIdDto productIdDto) {
        Product product = productMapper.selectOne(productIdDto.getId());
        if(product == null) {
            throw new BusinessRuntimeException("商品: " + productIdDto.getId() + "不存在");
        }
        List<AdditionalItem> additionalItems = productMapper.selectAdditionalItem(product.getProductExtendId());
        List<String> bannerList = productMapper.selectProductBanner(product.getProductId());
        List<String> detailImgList = productMapper.selectProductDetailImg(product.getProductId());
        ProductDetailRe re = assemblyProductDetail(product, additionalItems);
        re.setBannerList(bannerList);
        re.setImages(detailImgList);
        return re;
    }

    private ProductDetailRe assemblyProductDetail(Product product, List<AdditionalItem> additionalItems) {
        ProductDetailRe re = new ProductDetailRe();
        re.setId(product.getProductExtendId());
        re.setProductName(product.getProductName());
        re.setPrice(product.getPrice());
        re.setCutOffTime(product.getCutOffTime());
        re.setThumbnail(product.getThumbnail());

        if(!CollectionUtils.isEmpty(additionalItems)) {
            List<AdditionalItemRe> res = additionalItems.stream().map(this::convert2AdditionalItemRe).collect(Collectors.toList());
            re.setAdditionalItemRes(res);
        }
        return re;
    }

    private AdditionalItemRe convert2AdditionalItemRe(AdditionalItem item) {
        AdditionalItemRe re = new AdditionalItemRe();
        re.setId(item.getId());
        re.setName(item.getName());
        re.setPrice(item.getPrice());
        return re;
    }

    @Override
    public List<ProductListRe> getProductList(ProductQueryDto productQueryDto) {
        ProductListQuery query = new ProductListQuery();
        query.setCityId(productQueryDto.getCityId());
        query.setProductName(productQueryDto.getProductName());
        PageUtil.initPageInfo(query, productQueryDto);
        return productMapper.selectProductList(query);
    }

    @Override
    public List<CartItemProductRe> getCartItemProductList(List<Long> productIds) {
        if(CollectionUtils.isEmpty(productIds)) {
            return Lists.newArrayList();
        }
        // 根据id集合获取对应的商品列表
        List<Product> products = productMapper.selectProductListByIds(productIds);
        if(CollectionUtils.isEmpty(products)){
            return Lists.newArrayList();
        }
        return products.stream().map(this::convert2CartItemProductRe).collect(Collectors.toList());
    }

    private CartItemProductRe convert2CartItemProductRe(Product product) {
        CartItemProductRe re = new CartItemProductRe();
        re.setProductId(product.getProductExtendId());
        re.setProductPrice(product.getPrice());
        re.setProductName(product.getProductName());
        re.setThumbnail(product.getThumbnail());
        return re;
    }

    @Override
    public List<AdditionalItemRe> getCartAdditionalItemList(List<Long> itemIds) {
        if(CollectionUtils.isEmpty(itemIds)) {
           return Lists.newArrayList();
        }
        List<AdditionalItem> items = productMapper.selectAdditionalItemByIds(itemIds);
        if(CollectionUtils.isEmpty(itemIds)) {
            return Lists.newArrayList();
        }
        return items.stream().map(this::convert2AdditionalItemRe).collect(Collectors.toList());
    }

    @Override
    public ProductCheckRe checkProductValid(ProductValidDto dto) {
        Long productId = productMapper.selectValidProduct(dto.getProductId());
        if(productId == null) {
            throw new BusinessRuntimeException("商品不存在或已下架");
        }
        if(!CollectionUtils.isEmpty(dto.getProductAdditionalIds())) {
            Integer count = productMapper.selectValidAdditionalCount(dto.getProductAdditionalIds());
            if(count == null || dto.getProductAdditionalIds().size() != count) {
                throw new BusinessRuntimeException("附加项不存在");
            }
        }
        return new ProductCheckRe();
    }
}
