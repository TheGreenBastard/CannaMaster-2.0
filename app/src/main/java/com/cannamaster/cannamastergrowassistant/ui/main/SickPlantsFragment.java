package com.cannamaster.cannamastergrowassistant.ui.main;


import com.cannamaster.cannamastergrowassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Sick Plants and Problems Page Menu for navigation to individual articles
 */
public class SickPlantsFragment extends SectionsCardviewFragment {

    public static SickPlantsFragment newInstance(){
        SickPlantsFragment fragment = new SickPlantsFragment();
        return fragment;
    }

    @Override
    public List<SectionsCardviewItems> getArticles() {
        List<SectionsCardviewItems> sectionsCardviewItems = new ArrayList<>();

        // Nutrient Deficiencies
        sectionsCardviewItems.add(new SectionsCardviewItems("44", "Nutrient Deficiencies", "In order for cannabis to grow to its maximum potential you must provide the basic minerals for it to use.  If you don't provide enough your plant will suffer.  See our detailed chart of plant nutrient deficiencies",
                // topic to hit - NPK and what it means - Mixing Fertilizer - signs of deficiencies
                "The Article Text Goes Here.", "44",
                R.drawable.nutrient_deficiency));

        // Nitrogen (N)
        sectionsCardviewItems.add(new SectionsCardviewItems("45", "Nitrogen (N)", "Nitrogen is one of the big three nutrients that you must provide to your cannabis plant.  It is needed for healthy vegetive growth.  Just like anything, there is a thin line between just enough and too much of a good thing.",
                "The actual article text goes here", "45",
                R.drawable.p400x200));

        // Phosphorous (P)
        sectionsCardviewItems.add(new SectionsCardviewItems("46", "Phosphorous (P)", "Phosphorous is needed for helathy root development and flower production.  The concentration of Phosphorous typically the grower wants to substantially increase the concentration of phosphorous when flowering is initiated.",
                "The actual article text goes here", "46",
                R.drawable.p400x200));

        // Potassium (K)
        sectionsCardviewItems.add(new SectionsCardviewItems("47", "Potassium (K)", "Potassium is needed throughout the entire life of the plant.  It is responsible for ensuring the plants overall functions are performed optimally and maintained.",
                "The Actual article text goes here", "47",
                R.drawable.p400x200));

        // Calcium and Magnesium (Ca + Ma)
        sectionsCardviewItems.add(new SectionsCardviewItems("48", "Calcium And Magnesium (Ca + Ma)", "Cannabis is a whore for calcium and magnesium.  Besides the 'Big Three' you will need to ensure there is enough Calcium and Magnesium in the grow medium or your plants will suffer.  Whether you add it chemically or naturally you're plants will thank you.",
                "The actual article text goes here", "48",
                R.drawable.p400x200));

        // Micro Nutrients
        sectionsCardviewItems.add(new SectionsCardviewItems("49", "Micro Nutrients", "There are quite a few micro nutrients your plant will need as well, these elements are not needed in great quantities but if they aren't present at all you're plant will start to show you signs of stress.",
                "The actual article text goes here", "49",
                R.drawable.p400x200));

        // Nutrient Toxicity (Burn)
        sectionsCardviewItems.add(new SectionsCardviewItems("50", "Nutrient Toxicity (Burn)", "Just as you can lack nutrients, you can also provide too many nutrients causing a detrimental effect to your plants.  Are you sure you can't have a deficiency but still are seeing issues? Our detailed section on plant toxicities can help",
                "The Article Text Goes Here.", "50",
                R.drawable.p400x200));

        // Bugs And Other Pests
        sectionsCardviewItems.add(new SectionsCardviewItems("51", "Bugs And Other Pests", "You can provide and care for your plants flawlessly and still have a horrible experience if the wrong pest creeps into your grow.  Identify your pest, weather it is an insect or larger animal, we'll show you how to avoid an infestation and what to do if you are already infested.",
                "The Article Text Goes Here.", "51",
                R.drawable.bugs_on_leaves));

        // Spider Mites
        sectionsCardviewItems.add(new SectionsCardviewItems("52", "Spider Mites", "These tiny bastards can infest your entire grow in no time at all.  Spider mites are a cannabis growers worst nightmare.  They can kill entire plants in a matter of days if not treated promptly.",
                // Image Attribution
                "The actual article text goes here", "52",
                R.drawable.spider_mite_zoom));

        // Thrips
        sectionsCardviewItems.add(new SectionsCardviewItems("53", "Thrips", "Short SectionsCardview Description",
                // Image Attribution
                "The actual article text goes here", "53",
                R.drawable.thrip));

        // Budworms
        sectionsCardviewItems.add(new SectionsCardviewItems("54", "Budworms", "A common outdoor pest are budworms.  These tiny green caterpillars burrow inside your buds and eat them from the inside out.  500 of these worms can eat a pound of cannabis in a day.  Find out what you can do to combat this destructive beast.",
                // Image Attribution
                "The actual article text goes here", "54",
                R.drawable.budworm));

        // Soil Gnats
        sectionsCardviewItems.add(new SectionsCardviewItems("55", "Soil Gnats", "Soil gnats are more of a nuisance rather than an actual threat to your grow, but in extreme cases they can cause issues with your roots.  There are ways to control soil gnats without affecting your plant in the slightest way.","The Actual article text goes here", "55",
                R.drawable.soil_gnats));

        // Over/Under Watering
        sectionsCardviewItems.add(new SectionsCardviewItems("56", "Over/Under Watering", "One of the hardest aspects of cultivation for new growers is learning how and when to water.  Typically inexperienced growers will over water thier plants without realizing the harm they are causing.",
                "The actual article text goes here", "56",
                R.drawable.p400x200));

        // Mammals (Deer, Rabbits, Squirrels, etc)
        sectionsCardviewItems.add(new SectionsCardviewItems("57", "Mammals (Deer, Rabbits, Squirrels, etc)", "Typically when you think of garden pests destroying your plants, insects come to mind, but our mammal brethren are equally responsible for their share of outdoor plant damage.  Learn the signs and solutions to a four legged problem.",
                "The actual article text goes here", "57",
                R.drawable.deer));

        // My Plants Keep Getting Dug Up
        sectionsCardviewItems.add(new SectionsCardviewItems("58", "My Plants Keep Getting Dug Up", "When planting outdoors it is quite common to walk back to the spot you planted your healthy girl the day before only to find her sitting with her roots hanging out next to the hole you dug.  What Happened?",
                "The actual article text goes here", "58",
                R.drawable.dig_shovel));

        // Don't Forget Human Pests
        sectionsCardviewItems.add(new SectionsCardviewItems("59", "Don't Forget Human Pests", "When growing outdoors you must take special precautions to ensure that you don't get any human pests in your garden as well as everything else Mother Nature throws at you.  Humans pest could arguably be the worst pest of all.",
                "The Article Text Goes Here.", "59",
                R.drawable.p400x200));

        // Ive Tried Everything, Now What?
        sectionsCardviewItems.add(new SectionsCardviewItems("60", "I've Tried Everything, Now What?", "You've already been through the above sections, tried your hardest and still problems persist?  Inside you will find some things you can do when you've exhausted all your options and still problems are not getting solved.",
                "The Article Text Goes Here.", "60",
                R.drawable.what_now));

        return sectionsCardviewItems;
    }

    @Override
    public int getColumnNumber() {
        return 1;
    }
}