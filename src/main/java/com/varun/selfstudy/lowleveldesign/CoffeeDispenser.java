package com.varun.selfstudy.lowleveldesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoffeeDispenser {

    /**
     * Controller
     * - ability to create dispenser obj
     * - start dispenser
     * - stop dispenser
     * <p>
     * CoffeeDispenserService
     * - add diff types of coffee
     * - add ingredients
     * - accept money
     * - make selection
     * - dispense coffee
     * - return money
     * <p>
     * IngredientService
     * - add ingredients to machine
     * - remove ingredients
     * <p>
     * RecipeService
     * - add coffee recipe
     * - builder or chain?
     * <p>
     * VOs
     * - Ingredient
     * - Recipe
     * - Coffee
     * - Money
     * -
     * <p>
     * enum
     * - DispenserState
     */

    class Ingredient {

        String name;

        double cost;

        public Ingredient(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }
    }

    class Coffee {

        String name;

        Recipe recipe;

        double cost;

        public Coffee(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Recipe getRecipe() {
            return recipe;
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }
    }

    class Recipe {

        String name;

        Map<Ingredient, Integer> ingredientsMap;

        List<Ingredient> steps;

        public Recipe(String name) {
            this.name = name;
            ingredientsMap = new HashMap<>();
            steps = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void addIngredient(Ingredient ingredient) {
            ingredientsMap.put(ingredient, ingredientsMap.getOrDefault(ingredient, 0) + 1);
            steps.add(ingredient);
        }

        public Map<Ingredient, Integer> getIngredientsMap() {
            return ingredientsMap;
        }

        public List<Ingredient> getRecipeSteps() {
            return steps;
        }
    }

    class CoffeeDao {

        Map<String, Coffee> coffeeMap;

        public CoffeeDao() {
            this.coffeeMap = new HashMap<>();
        }

        public void addCoffee(Coffee coffee) {
            coffeeMap.put(coffee.getName(), coffee);
        }

        public void removeCoffee(Coffee coffee) {
            coffeeMap.remove(coffee.getName());
        }

        public Coffee getCoffee(String name) {
            if (coffeeMap.containsKey(name)) {
                return coffeeMap.get(name);
            }
            return null;
        }

        public Map<String, Coffee> getAllCoffees() {
            return coffeeMap;
        }
    }

    class CoffeeService {

        /**
         * add diff types of coffee
         * - add ingredients
         * - accept money
         * - make selection
         * - dispense coffee
         * - return money
         */

        CoffeeDao coffeeDao;

        public CoffeeService() {
            this.coffeeDao = new CoffeeDao();
        }

        public void addCoffee(Coffee c) {
            coffeeDao.addCoffee(c);
        }

        public void removeCoffee(Coffee c) {
            coffeeDao.removeCoffee(c);
        }

        public Coffee getCoffeeByName(String name) {
            return coffeeDao.getCoffee(name);
        }

        public double getCostOfCoffee(Coffee coffee) {
            if (coffee.getCost() > 0) {
                return coffee.getCost();
            }

            double cost = 0;
            Map<Ingredient, Integer> itemMap = coffee.getRecipe().getIngredientsMap();
            for (Ingredient item : itemMap.keySet()) {
                cost += item.cost * itemMap.get(item);
            }
            coffee.setCost(cost);
            return cost;
        }

        public Map<String, Coffee> getAlloffees() {
            return coffeeDao.getAllCoffees();
        }

    }

    class IngredientDao {

        Map<Ingredient, Integer> ingredientsMap;

        IngredientDao() {
            ingredientsMap = new HashMap<>();
        }

        public Map<Ingredient, Integer> getAllIngredients() {
            return ingredientsMap;
        }

        public void addIngredient(Ingredient i) {
            ingredientsMap.put(i, ingredientsMap.getOrDefault(i, 0) + 1);
        }

        public void removeIngredient(Ingredient i) {
            if (!ingredientsMap.containsKey(i)) {
                return;
            }
            int quantity = ingredientsMap.get(i);
            if (quantity == 1) {
                ingredientsMap.remove(i);
                return;
            }
            ingredientsMap.put(i, quantity - 1);
        }
    }

    class IngredientService {

        IngredientDao ingredientDao;

        public IngredientService() {
            this.ingredientDao = new IngredientDao();
        }

        public void addIngredient(Ingredient i) {
            ingredientDao.addIngredient(i);
        }

        public void removeIngredient(Ingredient i) {
            ingredientDao.removeIngredient(i);
        }

        public boolean areIngredientsAvailable(Map<Ingredient, Integer> ingredientsMap) { // can pass recipe obj instead
            Map<Ingredient, Integer> availableIngredients = ingredientDao.getAllIngredients();
            for (Ingredient i : ingredientsMap.keySet()) {
                if (!availableIngredients.containsKey(i)) {
                    return false;
                }
                int count = availableIngredients.get(i);
                if (count >= ingredientsMap.get(i)) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }

        public void removeIngredients(Map<Ingredient, Integer> ingredientsMap) { // can pass recipe obj instead
            Map<Ingredient, Integer> availableIngredients = ingredientDao.getAllIngredients();
            for (Ingredient i : ingredientsMap.keySet()) {
                for (int num = 0; num < ingredientsMap.get(i); num++) {
                    ingredientDao.removeIngredient(i);
                }
            }
        }
    }

    class Money { // can also name it as coin or note

        double value;

        public Money(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    class MoneyDao {

        double totalAmount;

        MoneyDao() {
            totalAmount = 0;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void addAmount(Money money) {
            totalAmount += money.getValue();
        }

        public void reduceAmount(Money money) {
            if (totalAmount < money.getValue()) {
                System.out.println("Total Amount is less than the amount to reduce. Throw Exception.");
            }
            totalAmount -= money.getValue();
        }

    }

    class PaymentService {

        MoneyDao moneyDao;

        Set<Double> acceptableDenominations;

        public PaymentService() {
            this.moneyDao = new MoneyDao();
            acceptableDenominations = new HashSet<>();
        }

        public boolean isValidMoney(List<Money> moneyList) {
            for (Money money : moneyList) {
                if (acceptableDenominations.contains(money.getValue())) {
                    return true;
                }
            }
            return false;
        }

        public boolean isCostLessThanPassedMoney(double cost, List<Money> moneyList) {
            double input = 0;
            for (Money money : moneyList) {
                input += money.getValue();
            }

            return input >= cost;
        }

        public void reduceAmount(Money money) {
            moneyDao.reduceAmount(money);
        }

        public void acceptAmount(List<Money> moneyList) {
            for (Money money : moneyList) {
                moneyDao.addAmount(money);
            }
        }

        public double getTotalMoneyPresentInTheSystem() {
            return moneyDao.getTotalAmount();
        }

        public void addDenomination(Money money) {
            acceptableDenominations.add(money.value);
        }
    }

    enum CoffeeDispenserState {
        ACTIVE,
        MONEY_ADDED,
        MAKE_SELECTION,
        ACCEPT_MONEY,
        DISPENSE_COFFEE,
        INCORECT_MONEY,
        REFUND_MONEY,
        MISSING_INGREDIENTS,
        INACTIVE
    }

    class CoffeeController {

        CoffeeService coffeeService;

        PaymentService paymentService;

        IngredientService ingredientService;

        CoffeeDispenserState dispenserState;

        CoffeeController() {
            dispenserState = CoffeeDispenserState.INACTIVE;
        }

        public void changeState(CoffeeDispenserState state) {

            switch (dispenserState) {
                case INACTIVE:
                    if (CoffeeDispenserState.ACTIVE.equals(state)) {
                        dispenserState = state;
                        return;
                    }
                    System.out.println("Dispenser Inactive.");
                    return;
                case ACTIVE:
                    if (CoffeeDispenserState.INCORECT_MONEY.equals(state)) {
                        System.out.println("Incorrect Money added.");
                        dispenserState = state;
                        reset();
                        return;
                    }
                    if (CoffeeDispenserState.MONEY_ADDED.equals(state)) {
                        System.out.println("Money added.");
                        dispenserState = state;
                        return;
                    }
                case MONEY_ADDED:
                    if (CoffeeDispenserState.MAKE_SELECTION.equals(state)) {
                        System.out.println("Please make selection.");
                        dispenserState = state;
                        return;
                    }
                    if (CoffeeDispenserState.REFUND_MONEY.equals(state)) {
                        System.out.println("Refund initiated.");
                        dispenserState = state;
                        reset();
                        return;
                    }
                case MAKE_SELECTION:
                    if (CoffeeDispenserState.MISSING_INGREDIENTS.equals(state)) {
                        System.out.println("Missing ingredients.");
                        dispenserState = state;
                        reset();
                        return;
                    }
                    if (CoffeeDispenserState.ACCEPT_MONEY.equals(state)) {
                        System.out.println("Money accepted.");
                        dispenserState = state;
                        return;
                    }
                case ACCEPT_MONEY:
                    if (CoffeeDispenserState.DISPENSE_COFFEE.equals(state)) {
                        System.out.println("Money accepted, dispensing coffee.");
                        dispenserState = state;
                        reset();
                        return;
                    }
                case INCORECT_MONEY:
                case MISSING_INGREDIENTS:
                    if (CoffeeDispenserState.ACTIVE.equals(state)) {
                        System.out.println("Reset to ACTIVE state.");
                        dispenserState = state;
                        return;
                    }
            }
        }

        public void reset() {
            System.out.println("Reseting to ACTIVE state.");
            changeState(CoffeeDispenserState.ACTIVE);
        }

        public void start() {
            System.out.println("Starting the machine.");
            reset();
        }

        public void stop() {
            System.out.println("Stopping the machine.");
            dispenserState = CoffeeDispenserState.INACTIVE;
        }

        public void dispense(List<Money> moneyList, String selection) {

            if (!paymentService.isValidMoney(moneyList)) {
                changeState(CoffeeDispenserState.INCORECT_MONEY);
                return;
            }
            changeState(CoffeeDispenserState.MONEY_ADDED);

            changeState(CoffeeDispenserState.MAKE_SELECTION);

            if (coffeeService.getCoffeeByName(selection) != null) {
                Coffee coffee = coffeeService.getCoffeeByName(selection);
                if (ingredientService.areIngredientsAvailable(coffee.getRecipe().getIngredientsMap())) {
                    if (paymentService.isCostLessThanPassedMoney(coffeeService.getCostOfCoffee(coffee), moneyList)) {
                        changeState(CoffeeDispenserState.ACCEPT_MONEY);
                        paymentService.acceptAmount(moneyList);
                        ingredientService.removeIngredients(coffee.getRecipe().getIngredientsMap());
                        changeState(CoffeeDispenserState.DISPENSE_COFFEE);
                    } else {
                        System.out.println("Amount passed is less than cost of coffee. Refunding momey.");
                        changeState(CoffeeDispenserState.REFUND_MONEY);
                    }
                } else {
                    changeState(CoffeeDispenserState.MISSING_INGREDIENTS);
                }
            } else {
                System.out.println("Coffee not found by the name. Refunding money.");
                changeState(CoffeeDispenserState.REFUND_MONEY);
            }
            // check if valid money
            // - if not change state to incorrect money and then to active
            // - if yes, change state to accept money, make selection
            //  - check if ingredients available
            //    - if not, change state missing ingredients, then to active
            //    - if yes, accept money, add to totalAmt, reduce ingredients, change state to dispense coffee, then to active
            //  -

        }

        public CoffeeService getCoffeeService() {
            return coffeeService;
        }

        public void setCoffeeService(CoffeeService coffeeService) {
            this.coffeeService = coffeeService;
        }

        public PaymentService getPaymentService() {
            return paymentService;
        }

        public void setPaymentService(PaymentService paymentService) {
            this.paymentService = paymentService;
        }

        public IngredientService getIngredientService() {
            return ingredientService;
        }

        public void setIngredientService(IngredientService ingredientService) {
            this.ingredientService = ingredientService;
        }
    }

    private void testMainFlow() {
        CoffeeService coffeeService = new CoffeeService();
        IngredientService ingredientService = new IngredientService();
        PaymentService paymentService = new PaymentService();

        CoffeeController controller = new CoffeeController();
        controller.setCoffeeService(coffeeService);
        controller.setPaymentService(paymentService);
        controller.setIngredientService(ingredientService);

        addCoffeeRecipes(coffeeService, ingredientService);
        addAcceptableDenominations(paymentService);

        controller.start();

        List<Money> moneyList = new ArrayList<>();
        moneyList.add(new Money(10));
        moneyList.add(new Money(10));
        moneyList.add(new Money(10));
        controller.dispense(moneyList, "Cappucino");

        System.out.println("Total money present in the system: " + paymentService.getTotalMoneyPresentInTheSystem());
        controller.stop();
    }

    public static void main(String[] args) {
        CoffeeDispenser cd = new CoffeeDispenser();
        cd.testMainFlow();
    }

    private void addAcceptableDenominations(PaymentService paymentService) {
        Money fives = new Money(5);
        Money tens = new Money(10);
        Money twenties = new Money(20);
        paymentService.addDenomination(fives);
        paymentService.addDenomination(tens);
        paymentService.addDenomination(twenties);
    }

    private void addCoffeeRecipes(CoffeeService coffeeService, IngredientService ingredientService) {

        Ingredient milk = new Ingredient("Milk", 10);
        Ingredient coffeePowder = new Ingredient("CoffeePowder", 10);
        Ingredient sugar = new Ingredient("Sugar", 5);
        Ingredient water = new Ingredient("Water", 5);
        Ingredient cocoPowder = new Ingredient("CocoPowder", 10);

        // adding 5 units of each ingredient initially
        for (int i = 0; i < 5; i++) {
            ingredientService.addIngredient(milk);
            ingredientService.addIngredient(coffeePowder);
            ingredientService.addIngredient(sugar);
            ingredientService.addIngredient(water);
            ingredientService.addIngredient(cocoPowder);
        }

        Coffee c1 = new Coffee("Cappucino");
        Recipe r1 = new Recipe(c1.getName() + "-Recipe");
        r1.addIngredient(milk);
        r1.addIngredient(coffeePowder);
        r1.addIngredient(sugar);
        r1.addIngredient(water);
        c1.setRecipe(r1); // totalCost = 30

        Coffee c2 = new Coffee("Latte");
        Recipe r2 = new Recipe(c2.getName() + "-Recipe");
        r2.addIngredient(milk);
        r2.addIngredient(coffeePowder);
        r2.addIngredient(cocoPowder);
        r2.addIngredient(sugar);
        c2.setRecipe(r2); // totalCode = 35

        coffeeService.addCoffee(c1);
        coffeeService.addCoffee(c2);
    }

}
