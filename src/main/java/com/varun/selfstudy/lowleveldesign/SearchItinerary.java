package com.varun.selfstudy.lowleveldesign;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class SearchItinerary {

    public static void main(String args[]) throws Exception {
        final List<Route> routes = Route.getExampleRoutes();
        final List<Accommodation> accommodations = Accommodation.getExampleAccommodations();
        final SearchCriteria searchCriteria = new SearchCriteria("Amsterdam", LocalDate.parse("2022-09-10"), 15, 5000);
        yourSolution(searchCriteria, routes, accommodations);
    }

    public static void yourSolution(SearchCriteria searchCriteria, List<Route> routes, List<Accommodation> accommodations) {
        /*
            Enter your code here.

            // 1. create an adj list
            // 2. put the origin on stack
            // 3. for each dest for origin
            //      if dayOfFlight of dest > numOfDaysPassedByUser return 0
            //      if dest == origin, then calc numOfDays and totalPrice (this includes accomodation prices as well)
            //      put on stack
            //

            Print result to stdout.
        */
        Map<String, List<Route>> adjList = new HashMap<>();
        for (Route route : Route.getExampleRoutes()) {
            if (!adjList.containsKey(route.getOrigin())) {
                adjList.put(route.getOrigin(), new ArrayList<>());
            }
            adjList.get(route.getOrigin()).add(route);
        }
        // for (String key : adjList.keySet()) {
        //     Arrays.sort(adjList.get(key));
        // }

        List<List<Route>> possibleRoutes = new ArrayList<>();
        dfs(searchCriteria.getOrigin(), searchCriteria.getOrigin(), adjList, possibleRoutes, new ArrayList<>());

        Map<String, Integer> cityToAmtMap = new HashMap<>();
        for (Accommodation city : Accommodation.getExampleAccommodations()) {
            cityToAmtMap.put(city.getLocation(), city.getPricePerNight());
        }

        System.out.println(possibleRoutes);
//        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o2.getNumOfDays() == o1.getNumOfDays
//                ? o1.getAmount() - o2.getAmount()
//                : o2.getNumOfDays() - o1.getNumOfDays());

        // // validate each route and calc amount
        // for (List<Route> possibleRoute : possibleRoutes) {
        //     // validate
        //     if (isValidRoute(possibleRoute, searchCriteria, routes)) {
        //         // calc amt
        //         // max(amt, max);
        //     }

        // }

    }

    static boolean isValidRoute(List<Route> possibleRoute, SearchCriteria searchCriteria, List<Route> routes) {
        if (possibleRoute.get(0).getDate().isBefore(searchCriteria.getStartDate())) {
            return false;
        }

        // int numOfDays =
        // for (Route city : possibleRoute) {

        //     if (city.getDate())

        // }

        return true;
    }

    static void dfs(String start, String dest, Map<String, List<Route>> adjList, List<List<Route>> possibleRoutes, List<Route> currentPath) {
        if (start == dest && !currentPath.isEmpty()) {
            possibleRoutes.add(new ArrayList<>(currentPath));
            return;
        }

        for (Route neighbour : adjList.get(start)) {
//            if (visited.contains(neighbour)) {
//                continue;
//            }
            currentPath.add(neighbour);
            dfs(neighbour.getDestination(), dest, adjList, possibleRoutes, currentPath);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    static class SearchCriteria {

        private String origin;

        private LocalDate startDate;

        private int maxDays;

        private int budget;

        public SearchCriteria(String origin, LocalDate startDate, int maxDays, int budget) {
            this.origin = origin;
            this.startDate = startDate;
            this.maxDays = maxDays;
            this.budget = budget;
        }

        public String getOrigin() {
            return origin;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public int getMaxDays() {
            return maxDays;
        }

        public int getBudget() {
            return budget;
        }
    }

    static class Accommodation {

        private String location;

        private int pricePerNight;

        public Accommodation(String location, int pricePerNight) {
            this.location = location;
            this.pricePerNight = pricePerNight;
        }

        static List<Accommodation> getExampleAccommodations() {
            List<Accommodation> accommodations = new ArrayList<>();
            accommodations.add(new Accommodation("Paris", 300));
            accommodations.add(new Accommodation("London", 500));
            accommodations.add(new Accommodation("Lisbon", 200));
            return accommodations;
        }

        public String getLocation() {
            return location;
        }

        public int getPricePerNight() {
            return pricePerNight;
        }
    }

    static class Route {

        private String origin;

        private String destination;

        private LocalDate date;

        private int price;

        public Route(String origin, String destination, LocalDate date, int price) {
            this.origin = origin;
            this.destination = destination;
            this.date = date;
            this.price = price;
        }

        static List<Route> getExampleRoutes() {
            List<Route> routes = new ArrayList<>();
            routes.add(new Route("Amsterdam", "Paris", LocalDate.parse("2022-09-10"), 150));
            routes.add(new Route("Paris", "London", LocalDate.parse("2022-09-15"), 200));
            routes.add(new Route("Paris", "Lisbon", LocalDate.parse("2022-09-14"), 40));
            routes.add(new Route("Lisbon", "Amsterdam", LocalDate.parse("2022-09-20"), 100));
            routes.add(new Route("Lisbon", "London", LocalDate.parse("2022-09-17"), 100));
            routes.add(new Route("London", "Amsterdam", LocalDate.parse("2022-09-21"), 150));
            return routes;
        }

        public String getOrigin() {
            return origin;
        }

        public String getDestination() {
            return destination;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getPrice() {
            return price;
        }
    }
}

