package com.cannamaster.cannamastergrowassistant.ui.main;


import com.cannamaster.cannamastergrowassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Tips And Tricks Page Menu for navigation to individual articles
 */
public class TipsAndTricksFragment extends SectionsCardviewFragment {

    public static TipsAndTricksFragment newInstance() {
        TipsAndTricksFragment fragment = new TipsAndTricksFragment();
        return fragment;
    }

    @Override
    public List<SectionsCardviewItems> getArticles() {

        List<SectionsCardviewItems> sectionsCardviewItems = new ArrayList<>();

        // Plan For Loss
        sectionsCardviewItems.add(new SectionsCardviewItems("17", "Plan For Loss", "Unless you are growing the maximum amount of plants by law you should always plan for a loss.  Not every plant makes it all the way to harvest, especially outdoors.",
                "Even with your best efforts, there is no way to save every plant your attempt to cultivate.  Genetics and unknown factors can cause unforeseen losses.  If you have never lost a plant, then you haven’t been growing long enough.\n" +
                        "\n" +
                        "Overcompensating may not be an option if you are growing legally within the constraints of your local laws.  Many municipalities restrict your maximum number of rooted plants.  Consult your local regulations before padding your plant count.\n" +
                        "\n" +
                        "Losses tend to be greater outdoors than indoors due to not being able to babysit outdoor plants 24/7.  Typically if you harvest half of the plants you initially put outdoors you are doing good.  Where this number indoors would be drastically bad.\n" +
                        "\n" +
                        "Some plants will grow out just fine but be considerably slower in vegetative growth or flowering than others.  This is another reason to over compensate your plant numbers.  If you always have a couple plants on the bench, you can mix and match your flowering numbers in the case of a slow developer.\n" +
                        "\n" +
                        "Outdoors it is almost a given that not all your plants will make it all the way to harvest.  Pests, deer, and rippers all have their eyes set on your beautiful girls.  If you over plan for harvest and more plants than you anticipated make it all the way to harvest you will likely be faced with a problem you want to have.\n" +
                        "\n" +
                        "I mean really, when have you ever said, “damn, I just have too damn much marijuana.”?", "17",
                R.drawable.plan_for_loss));

        // When Should I Water?
        sectionsCardviewItems.add(new SectionsCardviewItems("18", "When Should I Water?", "One of the hardest aspects of growing cannabis for new growers to master is proper watering techniques and schedules.  Learn when to water and how to do it right.",
                "The Actual Article Text Goes Here", "18",
                R.drawable.water));

        // No Grows Rooms Are The Same
        sectionsCardviewItems.add(new SectionsCardviewItems("19", "No Grow Rooms Are The Same", "No matter how much you try to copy someone else's setup, it will work different in your hand than in theirs.  Why does my room act different than my buddy's?",
                "The Actual Article Text Goes Here", "19",
                R.drawable.grow_room_overview));

        // How To Get Seedless Bud
        sectionsCardviewItems.add(new SectionsCardviewItems("20", "How To Get Seedless Bud", "No one likes seeds and unless you are trying to create seed stock or breed a new strain, there is no reason to have seeds in your cannabis.  Find what you need to do to ensure a seedless crop.",
                "The actual article text goes here", "20",
                R.drawable.nugs_in_hand));

        // Re-Potting (Potting Up)
        sectionsCardviewItems.add(new SectionsCardviewItems("21", "Re-potting (Potting Up)", "Cannabis can only get as large as the container it is growing in allows it to get.  Its roots tend to like to go down before spreading out, because of this cannabis likes a deeper pot rather than a wider pot.",
                "The Actual Article Text Goes Here", "21",
                R.drawable.potting_up));

        // Trim Your Leaves
        sectionsCardviewItems.add(new SectionsCardviewItems("22", "Trim Your Leaves", "You've spent all this time and hard work to get your plant covered in vigorous vegetation.  Why should you take off any of those beautiful leaves?",
                "The Actual Article Text Goes Here", "22",
                R.drawable.defoliate));

        // Topping
        sectionsCardviewItems.add(new SectionsCardviewItems("23", "Topping", "Topping is the practice of selectively trimming your seedling to promote growth of two favorties_listview colas as opposed to one favorties_listview cola.  The process is extremely simple. Find out how to grow a second favorties_listview cola and increase your yield.",
                "Topping is the practice of selectively trimming an adolescent cannabis plant so that instead of forming one favorties_listview cola, you for two favorties_listview cola’s prospectively doubling your end yield.  As well as producing two favorties_listview cola’s topping slows the vertical growth and concentrates more of the growth to the lower imagees which is beneficial during the beginning stages of your plant.\n" +
                        "\n" +
                        "Clones and mature plants are not able to be topped as they are taken from plants that have already started alternating nodes.  While the practice will still promote growth from the lower nodes only one favorties_listview cola will form.\n" +
                        "\n" +
                        "Before attempting to top your plants make sure you have at least 4 sets of nodes below the node you plan on making into 2 colas.  You want to ensure that the plant doesn't just turn into a dual spindle without side imageing.  The more imageing you have the larger your end yield will be.\n" +
                        "\n" +
                        "To actually top the plant you simply cut the stem after the top most node, cutting off the small growth that is sprouting from it. The plant will not suffer at all and you will notice it focusing its energy on different areas while it adjust to the topping.\n" +
                        "\n" +
                        "In about a week you will see 2 favorties_listview colas forming at the top of your plant extending from the node you cut above.\n" +
                        "\n" +
                        "If your plant grows fast enough you can possibly top it twice and create four favorties_listview colas but be aware that as soon as it starts alternating nodes, it is sexually mature and will no longer produce two colas from the topping site.\n" +
                        "\n" +
                        "When applied properly topping increases your end yield substantially.", "23",
                R.drawable.topping));

        // Easy Single Use Cloner
        sectionsCardviewItems.add(new SectionsCardviewItems("24", "Easy Single Use Cloner", "There are many options when it comes to cloning.  You will be hard pressed to find a cheaper or more effective method than this simple single use cloner.",
                "The actual article text goes here", "24",
                R.drawable.easy_cloner));

        // Perpetual Harvest
        sectionsCardviewItems.add(new SectionsCardviewItems("25", "Perpetual Harvest", "The only problem with growing your own cannabis is the waiting period between germination and harvest.    Learn how to rotate your plants and ensure a perpetual monthly harvest.",
                "Sometimes you can't wait and end up prematurely dissecting your plants for some immediate smoke affecting your end yield.", "25",
                R.drawable.p400x200));

        // Bigger Plants = Bigger Yield
        sectionsCardviewItems.add(new SectionsCardviewItems("26", "Bigger Plants = Bigger Yield", "In case you didn't know, bigger plants produce larger yields at harvest time.  Everything you need to know about creating monster girls that are more than fruitful.",
                "The actual article text goes here", "26",
                R.drawable.bigger_yields));

        // Grams Per Watt (GPW)
        sectionsCardviewItems.add(new SectionsCardviewItems("27", "Grams Per Watt (GPW)", "It can be hard to determine how well you are advancing with the long lul between harvest and if you are running more than a single strain, the numbers can be almost impossible.  That is, unless you know how to calculate 'Grams Per Watt'", "something", "27",
                R.drawable.gpw_calculator));

        // Sipping Trays
        sectionsCardviewItems.add(new SectionsCardviewItems("28", "Sipping Trays", "Once a plant gets large enough, it can be hard to keep it watered correctly, even with daily plant checks.  Sipping trays will solve your watering problems and even let you take a few days off for a well deserved vacation.",
                "The actual article text goes here", "28",
                R.drawable.sipping_trays));


        return sectionsCardviewItems;
    }

    @Override
    public int getColumnNumber() {
        return 1;
    }
}