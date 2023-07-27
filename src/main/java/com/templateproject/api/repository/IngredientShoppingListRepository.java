package com.templateproject.api.repository;

import com.templateproject.api.entity.IngredientShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface IngredientShoppingListRepository extends JpaRepository<IngredientShoppingList, Long> {

    @Transactional
    @Modifying
    @Query( value =
            "insert into ingredient_shopping_list(quantity, ingredient_id, shopping_list_id, unit_id)\n" +
            "select sum(ri.quantity / r.nb_person * rc.nb_person) as quantity, i.id, sl.id, u.id\n" +
            "from user usr\n" +
            "join shopping_list sl on usr.id = sl.user_id\n" +
            "join cart c on usr.id = c.user_id\n" +
            "join recipe_cart rc on c.id = rc.cart_id\n" +
            "join recipe r on rc.recipe_id = r.recipe_id\n" +
            "join recipe_ingredient ri on r.recipe_id = ri.recipe_id\n" +
            "join unit u on ri.unit_id = u.id\n" +
            "join ingredient i on ri.ingredient_id = i.id\n" +
            "where usr.id = :userId\n" +
            "group by i.id, u.id",
            nativeQuery = true)
    void insertIngredient(@Param("userId") Long userId);
}
