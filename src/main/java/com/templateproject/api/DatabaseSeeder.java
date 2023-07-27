package com.templateproject.api;
/*
import java.util.*;

import com.github.javafaker.Faker;
import com.templateproject.api.entity.*;
import com.templateproject.api.repository.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.templateproject.api.entity.Recipe.Budget.CHEAP;
import static com.templateproject.api.entity.Recipe.Budget.EXPENSIVE;
import static com.templateproject.api.entity.Recipe.Difficulty.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final Faker faker;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final StepRepository stepRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final RecipeCartRepository recipeCartRepository;
    public DatabaseSeeder(
            RecipeRepository recipeRepository,
            CategoryRepository categoryRepository,
            IngredientRepository ingredientRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            StepRepository stepRepository,
            UnitRepository unitRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            RecipeCartRepository recipeCartRepository
    ){
        this.faker = new Faker();
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.stepRepository = stepRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.recipeCartRepository = recipeCartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Category> allCategories = new ArrayList<>();
        List<Ingredient> allIngredients = new ArrayList<>();

        Recipe quatreQuart = new Recipe();
        Recipe quicheLorraine = new Recipe();
        Recipe pizzaRegina = new Recipe();
        Recipe spaghettiCarbonara = new Recipe();
        Recipe cupcake = new Recipe();
        Recipe boeufbourguignon = new Recipe();

        this.mockCreateUsers();
        this.mockCreateUnits();
        this.mockCreateCategories(allCategories);
        this.mockCreateIngredients(allIngredients);

        this.mockQuatreQuart(quatreQuart);
        this.mockQuicheLorraine(quicheLorraine);
        this.mockPizzaRegina(pizzaRegina);
        this.mockSpaghettiCarbonara(spaghettiCarbonara);
        this.mockCupCake(cupcake);
        this.mockBoeufBourguignon(boeufbourguignon);

        this.mockRandomFaker(10, allCategories, allIngredients);


        //CREATE NEW CART
        Cart firstCart = new Cart();
        firstCart.setId(1L);
        firstCart.setUser(userRepository.getReferenceById(1L));
        cartRepository.save(firstCart);


        //CREATE 3 RECIPE CARTS ITEMS
        RecipeCart rcFirstItem = new RecipeCart();
        rcFirstItem.setRecipeCart(firstCart);
        rcFirstItem.setRecipe(quatreQuart);
        rcFirstItem.setNbPerson(5);
        recipeCartRepository.save(rcFirstItem);

        RecipeCart rcSecondItem = new RecipeCart();
        rcSecondItem.setRecipeCart(firstCart);
        rcSecondItem.setRecipe(pizzaRegina);
        rcSecondItem.setNbPerson(4);
        recipeCartRepository.save(rcSecondItem);

        RecipeCart rcThirdItem = new RecipeCart();
        rcThirdItem.setRecipeCart(firstCart);
        rcThirdItem.setRecipe(boeufbourguignon);
        rcThirdItem.setNbPerson(2);
        recipeCartRepository.save(rcThirdItem);

        //ADD ALL RECIPE CART ITEMS TO A LIST
        List<RecipeCart> rcFirstUserCart = new ArrayList<>();
        rcFirstUserCart.add(rcFirstItem);
        rcFirstUserCart.add(rcSecondItem);
        rcFirstUserCart.add(rcThirdItem);

        //SET LIST TO CART AND SAVE
        firstCart.setRecipeList(rcFirstUserCart);
        cartRepository.save(firstCart);




    }

    public void mockQuatreQuart(Recipe myRecipe) {
        this.mockSetRecipe(myRecipe,"ma nouvelle recette de quatre-quarts",0.25f,0.75f,"https://i.imgur.com/R0PHxEv.png",1L);
        myRecipe.setDifficulty(EASY);
        myRecipe.setBudget(CHEAP);
        this.mockSetCategory(myRecipe,1L);
        Long qqIngredients[] = { 1L, 2L, 3L, 4L, 5L, 6L };
        Float qqQuantity[] = { 250.0f, 250.0f, 250.0f, 1.0f, 1.0f, 4.0f };
        Long qqUnit[] = { 1L, 1L, 1L, 3L, 3L, 4L};
        this.mockSetRecipeIngredients(myRecipe, qqIngredients);
        recipeRepository.save(myRecipe);
        String qqSteps[] = {
                "Pesez les œufs, et prenez ensuite le même poids en beurre, en sucre et en farine.",
                "Battez les jaunes d'œufs avec le sucre, et versez-y le beurre fondu, ensuite la farine par petites quantités et enfin les blanc des œufs battus en neige.",
                "Parfumez la pâte avec de l'extrait de vanille ou des zestes de citron.",
                "Versez dans une moule à cake beurré, et enfournez pendant 45 min à 160°C (thermostat 5-6) ; bien surveiller la cuisson.",

        };
        this.mockCreateSteps(myRecipe.getId(), qqSteps);
        this.mockCreateRecipeIngredients(myRecipe, qqIngredients, qqQuantity, qqUnit);
    }

    public void mockQuicheLorraine(Recipe myRecipe) {
        this.mockSetRecipe(myRecipe,"la quiche lorraine par ma grand mere",0.30f,0.40f,"https://i.imgur.com/VTiA0vN.png",1L);
        myRecipe.setDifficulty(HARD);
        myRecipe.setBudget(EXPENSIVE);
        this.mockSetCategory(myRecipe,4L);
        Long qlIngredients[] = { 3L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L };
        Float qlQuantity[] = {200.0f, 200.0f, 30.0f, 20.0f, 1.0f, 1.0f, 3.0f, 20.0f, 1.0f};
        Long qlUnit[] = { 1L, 1L, 1L, 5L, 4L, 4L, 4L, 5L, 4L};
        this.mockSetRecipeIngredients(myRecipe, qlIngredients);
        recipeRepository.save(myRecipe);
        String qlSteps[] = {
                "Préchauffer le four à 180°C (thermostat 6). Etaler la pâte dans un moule",
                "la piquer à la fourchette. Parsemer de copeaux de beurre.",
                "Faire rissoler les lardons à la poêle puis les éponger avec une feuille d'essuie-tout.",
                "Battre les oeufs, la crème fraîche et le lait.",
                "Ajouter les lardons.",
                "???",
                "Profit",
        };
        this.mockCreateSteps(myRecipe.getId(), qlSteps);
        this.mockCreateRecipeIngredients(myRecipe, qlIngredients, qlQuantity, qlUnit);
    }

    public void mockPizzaRegina(Recipe myRecipe) {
        this.mockSetRecipe(myRecipe,"pizza regina maison",0.25f,0.75f,"https://i.imgur.com/hTZIyIK.png",1L);
        myRecipe.setDifficulty(MODERATE);
        myRecipe.setBudget(Recipe.Budget.MODERATE);
        this.mockSetCategory(myRecipe,4L);
        Long prIngredients[] = { 1L, 6L, 2L, 8L, 9L, 4L, 11L, 12L, 7L };
        Float prQuantity[] = {100.0f, 300.0f, 30.0f, 20.0f, 4.0f, 1.0f, 3.0f, 5.0f, 1.0f};
        Long prUnit[] = { 2L, 1L, 3L, 5L, 4L, 4L, 4L, 5L, 4L};
        this.mockSetRecipeIngredients(myRecipe, prIngredients);
        recipeRepository.save(myRecipe);
        String prSteps[] = {
                "Mettez la levure dans 10 cl d'eau tiède pour l'activer.",
                "Mélangez la farine, le sel et l'huile dans une grande jatte.",
                "Ajoutez la levure dissoute dans 15 cl d'eau tiède. Pétrissez pendant 5 minutes jusqu'à obtention d'une pâte homogène. Couvrez la jatte d'un torchon et laissez reposer la pâte pendant 45 minutes, elle doit doubler de volume.",
                "Ébouillantez les tomates 20 secondes, plongez-les dans l'eau froide, enlevez la peau, les graines et hachez finement la pulpe.",
                "Dans une poêle anti-adhérente faites chauffer une cuillere à soupe d'huile d'olive, faites dorer l'ail et l'oignon, ajoutez les tomates en morceaux, laissez réduire à feu moyen, salez, poivrez, ajoutez l'origan.",
                "Préparez le champignons, coupez-les en lamelles, le jambon en petits carrés, la mozzarella en fines tranches.",
                "Préchauffez le four à thermostat 8 (240°C). Étalez la pâte à pizza sur une planche farinée, façonnez-la en forme de disque. Huilez une plaque à pâtisserie, déposez la pâte sur la plaque. Huilez légèrement la pâte, étalez la sauce tomate.",
                "Garnissez de jambon, de champignons, salez, poivrez, répartissez les tranches de mozzarella, les olives et arrosez du reste d'huile d'olive.",
                "Glissez au four, 15 à 20 minutes, jusqu'à ce que la pâte soit dorée et le fromage fondu.",
        };
        this.mockCreateSteps(myRecipe.getId(), prSteps);
        this.mockCreateRecipeIngredients(myRecipe, prIngredients, prQuantity, prUnit);

    }

    public void mockSpaghettiCarbonara(Recipe myRecipe) {
        this.mockSetRecipe(myRecipe,"spaghetti à la carbonara",0.33f,0.33f,"https://i.imgur.com/7UI4JXf.png",1L);
        myRecipe.setDifficulty(EASY);
        myRecipe.setBudget(CHEAP);
        this.mockSetCategory(myRecipe,4L);
        Long scIngredients[] = { 5L, 6L, 3L, 8L, 9L, 4L, 1L, 12L, 7L };
        Float scQuantity[] = {100.0f, 300.0f, 30.0f, 20.0f, 4.0f, 1.0f, 3.0f, 5.0f, 1.0f};
        Long scUnit[] = { 1L, 1L, 1L, 5L, 4L, 4L, 4L, 1L, 4L};
        this.mockSetRecipeIngredients(myRecipe, scIngredients);
        recipeRepository.save(myRecipe);
        String scSteps[] = {
                "Portez à ébullition un faitout d'eau salée. Plongez-y les spaghetti et laissez-les cuire environ 12 min, jusqu'à ce qu'ils soient al dente.",
                "Pendant la cuisson des spaghetti, faites revenir les lardons à sec dans une poêle, jusqu'à ce qu'ils soient bien dorés.",
                "Baissez le feu et incorporez la crème fraîche. Salez légèrement, poivrez généreusement et ajoutez les jaunes d'oeufs, en fouettant pour qu'ils ne cuisent pas.",
                "Rectifiez l'assaisonnement.",
                "Egouttez les pâtes. Versez-les dans la sauteuse, mélangez et transvasez dans un plat de service.",
                "Servez en présentant le parmesan à part.",
        };
        this.mockCreateSteps(myRecipe.getId(), scSteps);
        this.mockCreateRecipeIngredients(myRecipe, scIngredients, scQuantity, scUnit);
    }

    public void mockCupCake(Recipe myRecipe) {
        this.mockSetRecipe(myRecipe,"cupcake facile inratable",0.33f,0.33f,"https://i.imgur.com/ddhLiaS.png",1L);
        myRecipe.setDifficulty(EASY);
        myRecipe.setBudget(CHEAP);
        this.mockSetCategory(myRecipe,1L);
        Long ccIngredients[] = {5L, 6L, 3L, 8L, 9L, 4L, 1L, 12L, 7L};
        Float ccQuantity[] = {100.0f, 300.0f, 30.0f, 20.0f, 4.0f, 1.0f, 3.0f, 5.0f, 1.0f};
        Long ccUnit[] = { 1L, 1L, 1L, 5L, 4L, 4L, 4L, 1L, 4L};
        this.mockSetRecipeIngredients(myRecipe, ccIngredients);
        recipeRepository.save(myRecipe);
        String ccSteps[] = {
                "Préparer les gâteaux : mélanger le beurre ramolli et le sucre.",
                "Battre les œufs en omelette. En mettre la moitié dans le mélange, remuer, puis mettre le reste et remuer à nouveau.",
                "Mettre la moitié de la farine dans le mélange et remuer, puis verser le reste petit à petit en remuant",
                "Verser dans les petits compartiment en papier (qui se trouve en grande surface).",
                "Cuire 15 min minimum au four préchauffé à thermostat 5/6, selon votre four (150 à 180°C). Surveiller la cuisson.",
                "Préparer le glaçage : mélanger tous les ingrédients jusqu'à obtenir un joli glaçage épais.",
                "Sur les petits gâteaux cuits et refroidis, ajouter le glaçage avec une poche à douille (ou un sac congélation dont vous aurez coupé un coin) et décorer de petits bonbons ou de petits grains de sucre coloré.",
                "Placer au frais pendant 1 heure minimum. Ils se conservent très bien.",
        };
        this.mockCreateSteps(myRecipe.getId(), ccSteps);
        this.mockCreateRecipeIngredients(myRecipe, ccIngredients, ccQuantity, ccUnit);
    }

    public void mockBoeufBourguignon(Recipe myRecipe) {
        this.mockSetRecipe(myRecipe,"la bourgogne dans votre assiette",0.33f,3.5f,"https://i.imgur.com/SKFaPnv.png",1L);
        myRecipe.setDifficulty(EASY);
        myRecipe.setBudget(Recipe.Budget.MODERATE);
        this.mockSetCategory(myRecipe,1L);
        Long bbIngredients[] = {5L, 6L, 3L, 8L, 9L, 4L, 1L, 12L, 7L};
        Float bbQuantity[] = {100.0f, 300.0f, 30.0f, 20.0f, 4.0f, 1.0f, 3.0f, 5.0f, 1.0f};
        Long bbUnit[] = { 1L, 1L, 1L, 5L, 4L, 4L, 4L, 1L, 4L};
        this.mockSetRecipeIngredients(myRecipe, bbIngredients);
        recipeRepository.save(myRecipe);
        String bbSteps[] = {
                "Tailler le bœuf en cubes de 3 à 4 cm de côte. Peler les oignons sans les écorcher. Peler et couper les carottes en rondelles pas trop fines (2mm au minimum). Peler l'ail et enlever le germe.",
                "Dans une grande cocotte, faire fondre le beurre. Ajouter les oignons entiers et les lardons. Faire revenir en remuant constamment. Lorsqu'ils sont dorés, les retirer avec un écumoire, et réserver.",
                "Dans la même cocotte, faire revenir les morceaux de viande à feu vif. Ajouter les carottes, et faire revenir encore 5 mn.",
                "Lorsque la viande est bien dorée, saupoudrer de farine (60g) et laisser roussir en remuant toujours",
                "Verser le bouillon (que vous aurez préparé en faisant fondre les 2 cubes de bouillon de viande dans 50 cl d'eau bouillante). Bien gratter les sucs. Remettre les lardons et les [...]",
                "Au bout de ce temps, ajouter les champignons émincés, et mettre à cuire encore une demi-heure. Retirer le bouquet garni et verser dans un plat Servir avec des pâtes [...]",
        };
        this.mockCreateSteps(myRecipe.getId(), bbSteps);
        this.mockCreateRecipeIngredients(myRecipe, bbIngredients, bbQuantity, bbUnit);
    }

    public void mockRandomFaker(int ntimes, List<Category> allCategories, List<Ingredient> allIngredients) {
        Random randCategories = new Random();
        int ubCategories = allCategories.size() - 1;
        Random randIngredient = new Random();
        int ubIngredient = allIngredients.size() - 1;

        for (int i = 0; i < ntimes; i++) {
            Recipe myRecipe = new Recipe();
            Long longRandomCategories = randCategories.nextLong(ubCategories) + 1;
            Long longRandomIngredients = randIngredient.nextLong(ubIngredient) + 1;
            int width = 300 + i;
            int height = 200 + i;
            //https://loremflickr.com/300/200/dish
            //http://placekitten.com/300/200
            String myLink = "https://loremflickr.com/" + width + "/" + height + "/restaurantfood" ;
            this.mockSetRecipe(myRecipe,faker.food().dish(),0.30f,0.40f,myLink,2L);
            this.mockSetCategory(myRecipe,longRandomCategories);
            Long mrIngredients[] = { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L };
            Float mrQuantity[] = {200.0f, 200.0f, 30.0f, 20.0f, 1.0f, 1.0f, 3.0f, 20.0f, 1.0f};
            Long mrUnit[] = { 1L, 1L, 1L, 5L, 4L, 4L, 4L, 5L, 4L};
            this.mockSetRecipeIngredients(myRecipe, mrIngredients);
            recipeRepository.save(myRecipe);
            String mrSteps[] = {
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            };
            this.mockCreateSteps(myRecipe.getId(), mrSteps);
            this.mockCreateRecipeIngredients(myRecipe, mrIngredients, mrQuantity, mrUnit);

        }

    }

    public void mockCreateRecipeIngredients(Recipe myRecipe, Long[] myIngredients, Float[] myQuantities, Long[] myUnits) {
        for(int i = 0; i < myIngredients.length; i++) {
            this.mockCreateRecipeIngredient(myIngredients[i], myQuantities[i], myRecipe, myUnits[i]);
        }
    }

    public void mockCreateRecipeIngredient(Long idingredient, float quantity, Recipe myRecipe, Long idunit) {
        RecipeIngredient myNewRecipeIngredient = new RecipeIngredient();
        myNewRecipeIngredient.setIngredient(ingredientRepository.getReferenceById(idingredient));
        myNewRecipeIngredient.setQuantity(quantity);
        myNewRecipeIngredient.setRecipe(recipeRepository.getReferenceById(myRecipe.getId()));
        myNewRecipeIngredient.setUnit(unitRepository.getReferenceById(idunit));
        recipeIngredientRepository.save(myNewRecipeIngredient);
    }

    public void mockSetCategory(Recipe myRecipe,Long idcategory) {
        Set myRecipeCategory = new HashSet();
        myRecipeCategory.add(categoryRepository.getReferenceById(idcategory));
        myRecipe.setRecipeCategories(myRecipeCategory);
    }

    public void mockSetRecipeIngredients(Recipe myRecipe, Long[] myIngredients) {
        Set myRecipeIngredient = new HashSet();
        for (int i = 0; i < myIngredients.length; i++) {
            myRecipeIngredient.add(ingredientRepository.getReferenceById(myIngredients[i]));
        }
        myRecipe.setRecipeIngredient(myRecipeIngredient);
    }

    public void mockCreateStep(List<Step> allStep, String description, Long idrecipe, int number) {
        Step newStep = new Step();
        newStep.setDescription(description);
        newStep.setRecipe(recipeRepository.getReferenceById(idrecipe));
        newStep.setNumber((short) number);
        allStep.add(newStep);
    }

    public void mockCreateSteps(Long idrecipe, String[] mySteps) {
        List<Step> allSteps = new ArrayList<>();
        for(int i = 0; i < mySteps.length ; i++) {
            this.mockCreateStep(allSteps, mySteps[i], idrecipe, i);
        }
        stepRepository.saveAll(allSteps);
    }

    public void mockSetRecipe(Recipe myRecipe, String title, float prepTime, float cookTime, String imageLink, Long iduser) {
        myRecipe.setTitle(title);
        myRecipe.setPrepTime(prepTime);
        myRecipe.setCookTime(cookTime);
        myRecipe.setImageLink(imageLink);
        //TODO: having issue with user that lead to recipe spoiled by user record and not repeated later
        //myRecipe.setUser(userRepository.getReferenceById(iduser));
    }

    public void mockCreateIngredient(List <Ingredient> allIngredients, String name) {
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(name);
        allIngredients.add(newIngredient);
    }

    public void mockCreateIngredients(List <Ingredient> allIngredients) {
        mockCreateIngredient(allIngredients,"Farine");
        mockCreateIngredient(allIngredients,"Sucre");
        mockCreateIngredient(allIngredients,"Beurre");
        mockCreateIngredient(allIngredients,"Levure Chimique");
        mockCreateIngredient(allIngredients,"Sucre Vanillé");
        mockCreateIngredient(allIngredients,"Oeufs");

        mockCreateIngredient(allIngredients,"Pate Brisé");
        mockCreateIngredient(allIngredients,"Lardons");
        mockCreateIngredient(allIngredients,"Creme Fraiche");
        mockCreateIngredient(allIngredients,"Sel");
        mockCreateIngredient(allIngredients,"Poivre");
        mockCreateIngredient(allIngredients,"Lait");
        mockCreateIngredient(allIngredients,"Muscade");

        for (int i = 0; i < 10; i++) {
            mockCreateIngredient(allIngredients,faker.food().ingredient());
        }
        ingredientRepository.saveAll(allIngredients);
    }

    public void mockCreateCategory(List<Category> allCategories, String name) {
        Category newCategory = new Category();
        newCategory.setName(name);
        allCategories.add(newCategory);
    }
    public void mockCreateCategories(List<Category> allCategories) {
        mockCreateCategory(allCategories,"Dessert");
        mockCreateCategory(allCategories,"Soupe");
        mockCreateCategory(allCategories,"Snack");
        mockCreateCategory(allCategories,"Plat Principal");
        mockCreateCategory(allCategories,"Salade");
        mockCreateCategory(allCategories,"Ragout");
        categoryRepository.saveAll(allCategories);
    }

    public void mockCreateUsers() {
        User graham = new User();
        graham.setPseudo("grahamMaster");
        graham.setEmail("graham.master@gmail.com");
        userRepository.save(graham);

        User eric = new User();
        graham.setPseudo("EricDupond");
        graham.setEmail("eric.dupond@gmail.com");
        userRepository.save(eric);
    }

    public void mockCreateUnit(List<Unit> myList,String name) {
        Unit newUnit = new Unit();
        newUnit.setName(name);
        myList.add(newUnit);
    }



    public void mockCreateUnits() {
        List<Unit> allUnits = new ArrayList<>();
        mockCreateUnit(allUnits, "grammes");
        mockCreateUnit(allUnits, "litres");
        mockCreateUnit(allUnits, "sachets");
        mockCreateUnit(allUnits, "unités");

        mockCreateUnit(allUnits, "centilitres");
        unitRepository.saveAll(allUnits);
    }

}
*/