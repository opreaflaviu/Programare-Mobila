package com.example.flaviuoprea.shopapp.di;
import com.example.flaviuoprea.shopapp.parts.products.ProductsActivity;

import dagger.Component;

/**
 * Created by flaviuoprea on 12/17/17.
 */
@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {

    void inject(ProductsActivity productsActivity);
}
