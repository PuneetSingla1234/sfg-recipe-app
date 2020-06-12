package guru.springframework.sfgrecipeapp.bootstrap;

import guru.springframework.sfgrecipeapp.domain.*;
import guru.springframework.sfgrecipeapp.repositories.CategoryRepository;
import guru.springframework.sfgrecipeapp.repositories.RecipeRepository;
import guru.springframework.sfgrecipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }
    public List<Recipe> getRecipes(){

        Optional<Category> veg= categoryRepository.findByDescription("Vegetarian");
        Optional<Category> american = categoryRepository.findByDescription("American");
        if(!veg.isPresent())
            throw new RuntimeException("Category not found");
        if(!american.isPresent())
            throw new RuntimeException("Category not found");
        Optional<UnitOfMeasure> tbsp= unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> cup=unitOfMeasureRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> noUnit=unitOfMeasureRepository.findByDescription("No Unit");
        Optional<UnitOfMeasure> tsp=unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> ounce= unitOfMeasureRepository.findByDescription("Ounce");
        if(!tbsp.isPresent())
            throw new RuntimeException("Unit not found");
        if(!cup.isPresent())
            throw new RuntimeException("Unit not found");
        if(!noUnit.isPresent())
            throw new RuntimeException("Unit not found");
        if(!tsp.isPresent())
            throw new RuntimeException("Unit not found");
        if(!ounce.isPresent())
            throw new RuntimeException("Unit not found");

        List<Recipe> recipes= new ArrayList<>(2);

        Recipe perfectPopcorn=new Recipe();
        perfectPopcorn.setDescription("Perfect Popcorn");
        perfectPopcorn.setCookTime(10);
        perfectPopcorn.setPrepTime(10);
        perfectPopcorn.setDifficulty(Difficulty.EASY);
        perfectPopcorn.setServing(2);
        perfectPopcorn.setSource("www.simplyrecipes.com");
        perfectPopcorn.setUrl("https://www.simplyrecipes.com/recipes/perfect_popcorn/");
        perfectPopcorn.setDirections("1 Heat the oil." + '\n' +
                "2 Put 3 or 4 popcorn kernels into the oil. Wait for the popcorn kernels to pop." + '\n' +
                "3 When the kernels pop, add the rest of the 1/3 cup of popcorn kernels in an even layer." + '\n' +
                "4 Cover the pot, remove from heat and count 30 seconds." +'\n' +
                "5 Return the pan to the heat." +'\n' +
                "6 Once the popping slows to several seconds between pops, remove the pan from the heat, remove the lid, and dump the popcorn immediately into a wide bowl." +'\n' +
                "7 Melt butter in the empty hot pan." +'\n' +
                "8 Sprinkle the popcorn with salt to taste.");

        Notes perfectPopcornNotes=new Notes();
        perfectPopcornNotes.setRecipeNotes("Make sure the inside of the pot is completely dry before heating the oil in it, or else the oil will sputter.");
        perfectPopcornNotes.setRecipe(perfectPopcorn);
        perfectPopcorn.setNotes(perfectPopcornNotes);

        perfectPopcorn.addIngredient(new Ingredient("coconut oil",new BigDecimal(3),tbsp.get()));
        perfectPopcorn.addIngredient(new Ingredient("High quality popcorn kernels",new BigDecimal(0.33),cup.get()));
        perfectPopcorn.addIngredient(new Ingredient("salt to taste",new BigDecimal(0),noUnit.get()));

        perfectPopcorn.getCategories().add(veg.get());
        perfectPopcorn.getCategories().add(american.get());
        recipes.add(perfectPopcorn);
        System.out.println("Recipe ID:"+perfectPopcorn.getId());

        Recipe cheesyPotato= new Recipe();
        cheesyPotato.setDescription("Cheesy Potato Casserole Recipe");
        cheesyPotato.setPrepTime(15);
        cheesyPotato.setCookTime(50);
        cheesyPotato.setDifficulty(Difficulty.MODERATE);
        cheesyPotato.setServing(8);
        cheesyPotato.getCategories().add(veg.get());
        cheesyPotato.setSource("www.simplyrecipes.com");
        cheesyPotato.setUrl("https://www.simplyrecipes.com/recipes/cheesy_potato_casserole/");
        cheesyPotato.setDirections("1 Preheat the oven and prepare the casserole dish: Preheat oven to 400F. Lightly butter or grease a 9x13 baking dish." +'\n'+
                "2 Make the cheese sauce: In a medium pot, melt 1/4 cup butter over medium heat. Add diced onion and cook for 3-4 minutes until onions turn translucent but not browned. Then add minced garlic and cook for 30 seconds." +'\n'+
                "3 Combine the potatoes and the sauce: In a large bowl add the frozen hash browns and pour the sauce over the hash browns. Stir together well to combine and transfer to the prepared baking dish. Smooth the top." +'\n'+
                "4 Bake the casserole: Place the casserole in the center rack of your oven and bake for 45 minutes at 400˚F. Then turn on the broiler and broil for 5 minutes on high to crisp up the top of the casserole. If your casserole dish is not broiler-safe, then turn the oven up to 500˚F and bake for 3 more minutes." +'\n'+
                "5 Serve: Let the casserole cool slightly but serve while warm." +'\n'+
                "6 Make the Crispy Serrano Rings (Optional DAD ADD): Slice serrano peppers into thin coins. Heat a few tablespoons of olive oil over medium heat in a small skillet. Once it is hot, add serrano coins and cook for 2-3 minutes until they start to brown. Remove the coins from the skillet and let drain on a paper towel. Season with a pinch of salt.");

        Notes cheesyPotatoNotes=new Notes();

        cheesyPotatoNotes.setRecipeNotes("This casserole reheats absolutely beautifully, which is why it’s such a popular bring-along dish.\n" +
                "\n" +
                "1. To make this cheesy potato casserole ahead of time: Assemble the casserole, cover it, and store in the fridge for up to two days, then bake as instructed.\n" +
                "2. You can also, bake the entire casserole. Let it cool, cover it and keep it in the fridge for up to four days. When ready to reheat it, just follow the baking instructions as if you’d just mixed it together. It might need an extra 10 minutes in the oven to take off the chill.\n" +
                "3. You can also freeze this casserole! I recommend freezing it after baking it so the casserole is cooked and you just have to reheat it from a frozen state. It will keep in the freezer for three to six months, and you should reheat it in a 350˚F oven until it’s warmed through.\n");
        cheesyPotato.setNotes(cheesyPotatoNotes);
        cheesyPotato.addIngredient(new Ingredient("butter",new BigDecimal(0.25), cup.get()));
        cheesyPotato.addIngredient(new Ingredient("yellow onion",new BigDecimal(1), noUnit.get()));
        cheesyPotato.addIngredient(new Ingredient("cloves garlic",new BigDecimal(3), noUnit.get()));
        cheesyPotato.addIngredient(new Ingredient("all- purpose flour",new BigDecimal(0.25), cup.get()));
        cheesyPotato.addIngredient(new Ingredient("vegetable",new BigDecimal(3), cup.get()));
        cheesyPotato.addIngredient(new Ingredient("grated cheddar cheese",new BigDecimal(2), cup.get()));
        cheesyPotato.addIngredient(new Ingredient("salt",new BigDecimal(0.5), tsp.get()));
        cheesyPotato.addIngredient(new Ingredient("black pepper",new BigDecimal(0.5), tsp.get()));
        cheesyPotato.addIngredient(new Ingredient("frozen hash browns",new BigDecimal(30), ounce.get()));
        recipes.add(cheesyPotato);

        return recipes;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("I'm in DataLoader class");
        recipeRepository.saveAll(getRecipes());
    }
}
