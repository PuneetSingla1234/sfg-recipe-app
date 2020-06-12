package guru.springframework.sfgrecipeapp.controllers;

import guru.springframework.sfgrecipeapp.domain.Recipe;
import guru.springframework.sfgrecipeapp.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndexControllerTest {
    IndexController indexController;
    @Mock
    RecipeServiceImpl recipeService;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController=new IndexController(recipeService);
    }

    @Test
    void testMockMVC() throws Exception{
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
    @Test
    void getIndexPage(){
        Set<Recipe> recipes=new HashSet<>();
        Recipe recipe1=new Recipe();
        recipe1.setDescription("Recipe1");

        Recipe recipe2=new Recipe();
        recipe2.setDescription("Recipe2");
        recipes.add(recipe1);
        recipes.add(recipe2);
        when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor=ArgumentCaptor.forClass(Set.class);
        String result=indexController.getIndexPage(model);

        assertEquals(result,"index");
        verify(recipeService,times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue().size(),2);
    }
}