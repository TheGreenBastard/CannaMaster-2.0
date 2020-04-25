package com.cannamaster.cannamastergrowassistant.ui.main;

import com.cannamaster.cannamastergrowassistant.R;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the Basics Page Cardview list for navigation to individual articles
 *
 * This contains all the data for the articles in Basics
 */
public class BasicsFragment extends SectionsCardviewFragment {

    public static BasicsFragment newInstance(){
        BasicsFragment fragment = new BasicsFragment();
        return fragment;
    }

    @Override
    public List<SectionsCardviewItems> getArticles() {
        List<SectionsCardviewItems> sectionsCardviewItems = new ArrayList<>();


        boolean add =

        // PH Is Everything
        sectionsCardviewItems.add(new SectionsCardviewItems("1", "PH Is Everything!", "PH may be something you have never heard of, but it's probably the most important part of your grow.  Find out what PH is and why it means everything.",
                "PH is a measurement of acidity in soil and water. The scale ranges from 1-14, with 1 being a base and 14 being an acid. Cannabis thrives at a PH level just under neutral (7.0 is neutral).\n" +
                "\n" +
                "Rain water, distilled water, and RO water all have a natural PH of 7.0. Cannabis can only absorb the maximum nutrients from its grow medium if the PH is between 6.4 and 7.0. Any value higher or lower will result in nutrient lockout causing the plant to stress or possibly even die if the PH level is not corrected.\n" +
                "\n" +
                "Even if your grow medium has the optimal level of nutrients available an unacceptable PH range will cause the plant to show signs of a deficiency even though you are not really deficient. An unbalanced PH will make it impossible to diagnose issues affecting your cannabis.\n" +
                "\n" +
                "PH is easily adjusted with either PH-up or a PH-down solution which can be bought at any reputable garden depot or fish store. It can also be adjusted with common household products such as distilled white vinegar or critic acid (lemon juice) to lower your PH or common household baking soda to raise your PH. Keep in mind that anything other than PH-up or Ph-down will add extra salts and raise your waters PPM (parts per million) value [more on that later].\n" +
                "\n" +
                "PH of water is relatively easy to measure even for a novice. You will need to purchase a method to test it. Available options include a digital PH meter, paper PH test strips or my personal favorite a simple PH drop test kit available anywhere fish are sold.\n" +
                "\n" +
                "Digital PH meters are the most expensive option although they are also the most accurate when properly calibrated. You need to buy test solution and calibrate your meter before each use to ensure an accurate reading.", "1",
                R.drawable.ph_bottles_image));

        // Grow Mediums
        sectionsCardviewItems.add(new SectionsCardviewItems("2", "Grow Mediums", "Before you sprout a single seed you will need to decided what medium to grow your cannabis. Many factors go into choosing the right grow medium for your specific situation.",
                "Before you begin your cannabis you need to decide on a grow medium. What will your plant grow up in?\n" +
                "\n" +
                "Soil\n" +
                "\n" +
                "The most common and arguably the most natural option is growing in good old fashioned soil. Not only is soil the way cannabis has been growing for countless Millenniums but it is also the most forgiving. When growing in soil you have the longest window how to fix an ailing plant.\n" +
                "\n" +
                "Final however is not a \"set it and forget it\" solution. Careful attention must still be paid to PH as well as nutrient levels. One benefit of soil grows is the ability to flush your medium if a problem arises.. Flushing I the act of running clean PH'd water through your grow container at 3x-10x the volume of the container. This essentially washes out excess nutrients and helps to PH balance the soil, potentially fixing two problems at once.\n" +
                "\n" +
                "Besides making the choice to go with a soil grow you will need to determine weather you plan on using chemical fertilizers or if you will be going all organic (for more on organics see the Advanced Techniques\" section of the app).\n" +
                "\n" +
                "If you decide to use chemical nutrients your soil preparation will be substantially quicker than preparing an organic soil. This is because organic growing requires microscopic microbes to colonize in the soil before it can provide nutrients to your plant.", "2",
                R.drawable.grow_medium_choices));

        // Starting Your Seeds
        sectionsCardviewItems.add(new SectionsCardviewItems("3", "Starting Your Seeds", "If you don't have access to clones you will need to start your grow the old fashioned way.  Learn how to start and store your seeds for the best chance at germination.",
                "It is imprinted in the seeds genetic code how to begin life but there are some things you can do to help the process along. \n" +
                "\n" +
                "First, you should be aware that while it may work, starting your seeds in the dirt is probably the worst way to get your girls going. There are many variables that come into play when starting directly in soil. \n" +
                "\n" +
                "Is it the right temperature? Is the soil damp enough? Is the soil too damp? Did the seed germinate yet?  Will the seed ever germinate or am I wasting grow space?  Is my soil PH on point?\n" +
                "\n" +
                "Let’s take a look at what seeds need to kick start life.  A seed is Mother Nature’s cryo-chamber.  A perfect vehicle for life to flourish at a later time.  Seeds are quite hardy and can lay dormant for decades or even Millennia before springing to life and showing their genetic muscles. Seeds have been found in tombs of ancient Egyptians and germinated what was thought to be an extinct strain of grain nearly 2000 years after they were placed.  This proves that any seed can survive a seemingly impossible amount of time and still do their job if stored under the proper conditions.\n" +
                "\n" +
                "Seeds like to be stored in a cool dark environment with a humidity that is not too high, but also not too dry.  A high humidity could cause the seeds to sprout and will also feed any mold that happens to latch on given seeds are organic and mold loves organics.  A humidity level of 30%-60% is acceptable for storing cannabis seeds long term.\n" +
                "\n" +
                "You do not want your seeds to see much light.  Although exposure to light on a short term basis is not harmful any long term exposure will start to break down your seeds and eventually they will no longer be viable.  Look at how a lawn ornament fades after a few summers under the sun.  It isn’t fading due to the wind or the rain, it is the solar radiation that breaks it down.  The sun will do the same to your seeds.  \n" +
                "\n" +
                "Left sit in a bottle on your desk for a few years and you will find that when you finally decide to try them in your garden, they no longer sprout.  However, if your grandfather kept a handful of seeds in an old desk drawer in the basement and you stumbled across them 20 or 30 years later, there is a good chance that some of those seeds, if not all, are still viable.  Environment is everything when storing seeds.\n" +
                "\n" +
                "Starting Your Seeds\n" +
                "\n" +
                "So, you’ve managed to keep your seeds happy and healthy. When it comes time to sprout them what should you do?  The idea is to sprout the seeds before putting them into soil so can determine their viability as well as not wasting grow space on a plant that is never going to come up.\n" +
                "\n" +
                "Start the process by gathering a plastic sandwich bag and a paper towel.  It does not matter if the plastic bag has a “zip lock” or is just a regular cheap sandwich bag.  The bag is only to contain the humidity and moisture while the seed germinates.\n" +
                "\n" +
                "Wet the paper towel and carefully fold it in half again and again until you get down to a size that will easily fit within the sandwich bag.  It does not need to cover the entire bag, you will want some breathing room so you dont break any roots when removing it after the seeds have sprouted.\n" +
                "\n" +
                "You will need to make sure the paper towel is not too wet.  You do not want it dripping wet like you just took it out of the pool.  Gently squeeze most of the water out of the paper towel, it really doesn’t take much.\n" +
                "\n" +
                "Next, you will need to spread your seeds on one half of the folded and now wet paper towel. Don’t worry about spacing them out perfectly, the roots will not get a chance to tangle in the short time they will be together.\n" +
                "\n" +
                "Once your seeds are on the wet paper towel fold the towel over on top of them so they are sandwiched between the layers of wet folded paper towel.  Now, put the moist paper towel with the seeds inside the plastic sandwich bag.  Make sure you either zip the bag shut or fold over the flap securely to contain the moisture within.\n" +
                "\n" +
                "Place the closed bag in a dark place with a temperature of between 70F and 75F.  Leave the plastic bag alone for 2-3 days.  It will take at least 2 days for the first seeds to sprout.  When you check the paper towel you should find at least some of the seeds to have cracked their shell open and started pushing out roots.\n" +
                "\n" +
                "If after 5-7 days no seeds have sprouted, or any seeds that haven’t sprouted are not going to sprout.  If it’s been a week and you havent seen a root yet, you can rest assured you wont see a root.  It’s time to start another batch of seeds and try agian.\n" +
                "\n" +
                "Be aware that once a seed starts to grow its tap root the root can grow an inch per day, so don’t forget about your seeds or you may find long roots and sprouts that have been light deprived long enough to ensure that if the starter leaves ever see the light of day they will just shrivel and die.\n" +
                "\n" +
                "Once a seed starts to grow it wants to get some light and start photosynthesizing.  A seedling does not require any nutrients for the first 2 weeks of life.  Everything it needs is stored within the seed itself.\n" +
                "\n" +
                "Never fertilize your plants before the 2 week mark.  You are much more likely to burn them with fertilizer when they are at such a tender stage.  They will do just fine with adequate water and a good substrate to anchor in.\n" +
                "\n" +
                "Once a seed is sprouted I like to put them into plastic “solo” cups, which make great seedling starters.  Your seedling is going to outgrow its plastic home in just a few weeks so be prepared to pot up in about 3 weeks or so.  Plants can be kept in small containers longer if you trim them properly and provide adequate nutrients to support the foliage above ground.  If you allow them to stay in a small container too long the plant will become root bound and display signs of stress in its foliage.  The only remedy for a rootbound plant is to transplant into a larger container.\n" +
                "\n" +
                "Some people like to start their seeds in rapid rooters or peat pots, which is perfectly fine since they dont need any nutrients at the first stages.  Although with peat pots or rapid rooters you will quickly run out of root space and find yourself transplanting rather soon after placing the sprouts.", "3", R.drawable.seeds));

        // What Will You Feed Them?
        sectionsCardviewItems.add(new SectionsCardviewItems("4", "What Will You Feed Them?", "Cannabis is a living, breathing organism.  It needs to eat in order to produce its flowers (buds).  What you feed your plant will affect what you get out of it.  Learn the common feeding and fertilization options.",
                "Before starting a crop of your own you will have to decide what you are going to feed your girls. No matter what your grow medium choice there are only two real choices when it comes to feeding. Chemical nutrients or organic nutrients. \n" +
                "\n" +
                "There are positive and negative aspects of both. So when it all comes down to it, it's really a personal preference. \n" +
                "\n" +
                "You will find no stronger performance other than chemical nutrients. They absolutely work and they work well, sometimes too well. If you are not careful you could easily burn your plants, especially if they are adolescent plants or seedlings. \n" +
                "\n" +
                "Chemical fertilizers tend to build up within the plant causing harsh taste and/or a crackle when burning, even with proper processing and a good cure. \n" +
                "\n" +
                "The majority of these issues can be avoided with proper flushing of the soil starting at least 2 weeks before harvest. \n" +
                "\n" +
                "Organic fertilizers usually come in the form of soil amendments although commercial pre mixed organic fertilizer a do exist. Traditionally these commercially available organics are far more expensive than their chemical counterpart. \n" +
                "\n" +
                "If organic soil is mixed and ‘cooked’ properly it shouldn't need any actual fertilizer all the way through to harvest. \n" +
                "\n" +
                "‘cooking’ is the term coined to describe the waiting period while you turn your soil before potting with it. This initial waiting period allows the beneficial microbes needed for optimal growth to fully colonize within the soil. \n" +
                "\n" +
                "Organic grows do enjoy a periodic boost though. This usually comes in the form of a tea consisting of various organic ingredients. There are as many organic tea recipes as there are cannabis strains. If you choose this route experiment with a few recipes and choose what works best for you. \n" +
                "\n" +
                "The most notable benefit of organics is the increased terpenes (smell and taste characteristics). Organics will bring out the true taste of your cannabis that is simply not possible with chemical fertilizers. \n" +
                "\n" +
                "One of the downsides of organics is that you will not get as good of a yield under the same conditions compared to chemical fertilizers.\n" +
                "\n" +
                "The choice really depends on your personal situation and preference. \n", "4",
                R.drawable.question_marks));

        // Proper Lighting Sources
        sectionsCardviewItems.add(new SectionsCardviewItems("5", "Proper Lighting Sources", "Without the sun, or something that mimics the sun, your plants can not grow.  Cannabis needs a lot of light to flourish to its fullest potential.  Find out if your lighting will stand up to your plants needs.",
                "When growing cannabis indoors not just any old light will do. You are trying to imitate the plant's natural grow environment which means you are trying to recreate as close as possible its natural native environment.\n" +
                "\n" +
                "Perhaps the most important of these variables is certainly light. At this point there is nothing on the market that comes even close to the spectrum and intensity of the sun, so we must do our best with the equipment we have available.\n" +
                "\n" +
                "Incandescent and Halogen Bulbs\n" +
                "\n" +
                "These light sources are simply unacceptable.  They do not put out anywhere near the correct spectrum for growing any kind of plant.  They are also ridiculously inefficient compared to any other option.\n" +
                "\n" +
                "If you try to grow with Incandescent or Halogen lights you are sure to witness an epic failure.  These lights will simply not grow happy plants.\n" +
                "\n" +
                "Fluorescent and Compact Fluorescent Bulbs\n" +
                "\n" +
                "Fluorescent and Compact Fluorescent Lights (CFL) can be an indoor gardener's secret weapon.  These lights are very efficient at projecting light a very small distance and with color options that span the entire spectrum you can tailor them to your specific situation.\n" +
                "\n" +
                "The most important aspect to remember when using fluorescent or CFL bulbs in your grow is their light intensity diminishes rapidly after 2’’.  Therefore you need to keep your bulbs within 2’’ of your plant to ensure they are getting all the possible light you can throw at them.\n" +
                "\n" +
                "Fluorescent and CFL lights work extremely well during the plant's vegetative cycle as the plant does not need near the light intensity as it requires during the flowering cycle.  Light arrays consisting of (4) dual bulb, 4’ long shop lights can effectively veg a 4’x4’ space with excellent results.\n" +
                "\n" +
                "Typical Fluorescent tube lights are not very effective during the cannabis flowering process.  It is difficult to position the lights at the correct distance from the plant.\n" +
                "\n" +
                "I have seen very successful grows from seed to completion using only CFL bulbs.  The benefit to CFL exclusive grows is the power consumption you save as these lights use very little power compared to other options.\n" +
                "\n" +
                "The rule of thumb with fluorescent and CFL lights has always been 100W of actual light (not equivalent ratings) for the first plant and 50W for each additional plant in the grow space.  This rule of thumb translates to HID lighting as well.  There is nothing wrong with providing more light than this baseline.  One thing your plants will never get too much of is light.\n" +
                "\n" +
                "Note that flowering exclusively under CFL is going to ensure a small harvest.  This is an option for experimentation and personal stash use.\n" +
                "\n" +
                "CFL bulbs do get hot, contrary to what you may have read.  When you encapsulate them within a sealed grow room you will need to adjust your airflow accordingly or heat will build up affecting your plants negatively.\n" +
                "\n" +
                "Metal Halide Bulbs (MH)\n" +
                "\n" +
                "Metal Halide lights are a popular industrial light solution used in everything from factory lighting to street lights.  These bulbs are considered High Intensity Discharge bulbs, producing a very effective light source that does not immediately dissipate.\n" +
                "\n" +
                "The length from the bulb that the light intensity starts to drastically diminish is determined by the bulb itself.  A 400W bulb will only effectively produce an intense light for 3ft before the lumens start to drop quick.  Where a 600W bulb will project almost another foot, and a 1000W light can project for 5’+, with proper reflection.\n" +
                "\n" +
                "Metal Halide lights naturally produce a mainly blue light spectrum, This is a wonderful option for getting your vegetative growth started fast and strong. Metal Halide bulbs are capable of penetrating below the initial canopy of your plants to provide light underneath your outer foliage.\n" +
                "\n" +
                "Recently many growers have started to suppliment Metal Halide alongside High Pressure Sodium Bulbs during flower to increase trichome production and some even claim to reduce overall flowering time by as much as a week.\n" +
                "\n" +
                "Metal Halide systems can be purchased for $100-$200 depending on manufacturer and equipment specifications.  Bulbs are typically $30-$80 which spans the gamut of ultra cheap generic bulbs to high output spectrum efficient options like Hortilux.\n" +
                "\n" +
                "Using metal halide alone during flowering is not a recommended practice.  Your cannabis flowers will want the red spectrum that is lacking in metal halide lights.\n" +
                "\n" +
                "High Pressure Sodium (HPS)\n" +
                "\n" +
                "High pressure sodium lights are the most commonly used light for flowering. These lights use High Intensity Discharge bulbs that are extremely effective in flowering cannabis plants.  The bulbs emit a more red spectrum of light which is ideal for bud growth during the flower cycle.\n" +
                "\n" +
                "High pressure sodium lights come in all the same flavors as metal halide lights, typical sizes are 1000W, 600W, 400W and 150W.\n" +
                "\n" +
                "Although high pressure sodium lights are ideal for flowering they are quite lacking during the vegetative growth cycle.  They simply do not produce enough blue light.  This leads to lanky stretched plants with lanky node spacing which is far from ideal when growing indoors.\n" +
                "\n" +
                "Indoor growers typically want dense foliage and tightly spaced nodes to maximize production given space constraints.\n" +
                "\n" +
                "What HPS bulbs lack in veg they more than make up for during the flowering cycle.  Cannabis plants absolutely love to flower under this light source.  The added intensity of the HID light penetrates the canopy better than fluorescent or LED lights can.  The only drawback is the power consumption which can not be avoided.\n" +
                "\n" +
                "Most indoor growers only utilize high pressure sodium lights during the flowering phase, choosing instead a more efficient light like fluorescent or an LED source during the vegetative cycle.  You will not see any benefit supplementing HPS lighting alongside another light source during veg so keep it on the bench ready to jump in as soon as you start the flowering process.\n" +
                "\n" +
                "Bulbs for high pressure sodium lights are almost on point with metal halide bulbs as far as price is concerned.  Typically costing $30-$80 depending on manufacturer and favorites_cardview_row.\n" +
                "\n" +
                "LED Lighting Systems\n" +
                "\n" +
                "The newest technology to take hold in indoor growing are LEDs (Light Emitting Diodes).  LED technology has progressed giant steps in the last few years, moving from underpowered 0.5W diodes to 5W diodes in only a matter of years.\n" +
                "\n" +
                "LED lighting can provide the most balanced spectrum of light that the plant actually uses without generating any spectrum that the plant can not use, like green light.  Other light sources produce small amounts of light that the plant simply can not convert into energy.  This light is essentially wasted as is the money you spend to generate it in the form of your electric bill.\n" +
                "\n" +
                "LEDs can provide light to your entire grow space for a fraction of the cost of traditional HID lighting.  This can be a deciding factor to those growers with a tight pocket.  The initial cost of LED lighting systems is typically more than its HID counterparts but the money you save every month on your electric bill can quickly add up.\n" +
                "\n" +
                "Not all LEDs are equal.  As well as differences in bulb power (Watts), every manufacturer has a slightly different arrangement of LEDs in their lighting system.  Some provide slightly more blue, some slightly more red or white.  There is no magic ratio to look for as your plants will be happy with any light they can turn into energy.\n" +
                "\n" +
                "If you are seriously considering an LED lightsource for your indoor grow, take the time to do some research on prospective companies and lighting system models.  Read peer reviews and ask fellow growers their experience with prospective light systems.  Doing your homework now could save you the expense of upgrading in the near future or paying for costly repairs of subpar equipment.\n" +
                "\n" +
                "LEDs have a long lifespan in comparison with HID bulbs.  \n" +
                "\n" +
                "LEDs are still the new kids on the block with respect to cannabis cultivation.  New advances are being made every year that increase both the efficiency of the light produced as well as the efficiency of the power consumed to generate it.\n" +
                "\n" +
                "LEDs can be a supplemental light source or a sole primary light source with excellent results.  Newer LED systems are more efficient and effective so be wary of buying used LED equipment without checking specifications and comparing them to the current technology.\n" +
                "\n" +
                "Green Light\n" +
                "\n" +
                "We all know that we aren’t supposed to interrupt the dark period of our sleeping girls.  There are however those times when you just have to get into the grow space during lights out to do something.  Whether it water, remove some leaves, or sing to them… Sometimes you just can’t avoid disturbing their slumber.\n" +
                "\n" +
                "As this is a primary cause of hermaphrodite plants if you must enter the grow room during lights out use a green bulb.  Cannabis plants can not see or process green light, so if you only show them green light they think the lights are out allowing you to work around them without causing any undue stress.\n" +
                "\n" +
                "Any green light will do.  You can buy special green CFL or LED lights but a regular bulb painted green or a CFL with green saran wrap around it would work just as well.  The outer covering of the bulb filters the light so only green shows through.  This is why we see green and no other colors.  The remaining colors of light are reflected back inside the bulb unable to escape and wake up your slumbering girls. Since the plants can not use green light we are able to sneak around without disturbing them.\n" +
                "\n" +
                "Be careful when entering and exiting your grow space that no light enters as you open and close the door.  Taking extreme precautions to work around your girls during lights out does no good if you negate everything by allowing unfiltered light to escape into the grow while you enter or exit.\n" +
                "\n" +
                "It is recommended that you purchase a green bulb of some sort rather than painting one yourself.  Amateur paint jobs tend to have small streaks where paint is absent.  Although these small gaps may not be visible to you, if they are present they can negatively affect your cannabis plants.", "5",
                R.drawable.man_thinking_lightbulb_head));

        // When To Turn Out The Lights
        sectionsCardviewItems.add(new SectionsCardviewItems("6", "When To Turn Out The Lights", "Cannabis is an annual plant, which means it completes one lifecycle per year.  starting in the spring and dying off sometime in the fall.  When we grow indoors we need to mimic the natural light cycles of the plant.  Find out how much light is just enough.",
                "Once you have vegged your plants out to a reasonable size it will be time to start the flowering process.  Cannabis is a photosensitive flowering plant, which means it determines when to flower based on the length of its dark period.\n" +
                "\n" +
                "Many tests have been made with cannabis and using various time cycles.  The general consensus for decades has been a 12/12 light cycle is ideal for indoor growing.  This means 12 hours of direct light and 12 hours of absolute dark.\n" +
                "\n" +
                "Any variance from this 12/12 standard could produce unwanted results or cause undue stress to your plant(s).  Smarter more creative growers than yourself have endlessly tried to improve on this old standard to absolutely no avail.  Don’t rock the boat, it won’t help your cannabis.\n" +
                "\n" +
                "A common question among new growers is ‘When should i actually start to flower?’  There is no magical time schedule for your veg cycle.  You want to decide when to start flowering based on the plant size not how long it takes to get there.\n" +
                "\n" +
                "Every strain is different with respect to how quickly it grows.  This can certainly be influenced by many factors including, but not limited to: light intensity, nutrient levels, proper PH balancing, humidity, air circulation, CO2 levels, genetic constraints, and another 100 issues that I’m simply not going to list.\n" +
                "\n" +
                "Due to all these possible variables we must judge our flowering time on plant structure rather than a timeline.  To determine your ideal flowering height you must first assess your grow room.  How much height do you have?  How much floor space do you have?  Can you move your light up any higher and if so how far?\n" +
                "\n" +
                "Cannabis has a large ‘stretch’ in the initial 3-4 weeks of flowering.  During this period the plant can grow as much as double or even triple its initial size when flowering was initiated.  This extension in height is measured from the top of your grow medium.  Therefore if your ceilings are 5’ (60’’) tall, and you start to flower with the plants at 2ft (24’’), you could see an additional 2’ (24’’) - 3’ (36’’) of growth by harvest.  Once you add this to say a 12’’-18’’ pot or hydroponic setup you are well past your maximum grow height when you account for your light source.\n" +
                "\n" +
                "If you do not prepare for this huge growth spurt you will end up with buds growing into your lights, burning, and blocking out the light to the rest of your flowering plants.\n" +
                "\n" +
                "Different strains have a large difference in stretch.  Indicas tend to stretch very little where plants leaning more toward the sativa side stretch quite a bit.  As you grow a strain more you will be able to judge its stretch more precisely.\n" +
                "\n" +
                "If you fail to flip your lights at the ideal time there are some things you can do to drop your canopy back down and at least keep the buds out of your lights.  The most common solution to this is simply bending the stalks over at a 45-90 degree angle and tying them with string to something secure within the grow.  Some people use tomato cages to both support their plants during flower as well as giving them something to tie onto in case they need to bring the girls down a bit.\n" +
                "\n" +
                "When tying your plants down, be aware that the stalks need to breathe.  If you tie too tight onto the stem you will cut off its circulation very similar to how a tourniquet works on you.  If you block off the nutrient highway to the plant, everything above that point will die.  It is best to use large non slipping loops when tieing down cannabis imagees.\n" +
                "\n" +
                "Try not to use fishing line as its high tensile strength and thin profile tend to cut into stalks quickly with very little pressure needed.  There’s nothing worse than seeing a fat cola on the ground a day after you decided that the fishing string would be fine for a week until you got something better.\n" +
                "\n" +
                "Another option to get plants down to size for flowering is to simply trim them.  How?  Any way you want.  I typically just pick a height and anything above that imaginary line gets cut off.  Similar to just taking a hedge shear and chopping it in half, or ⅓ depending on how much extra height i need to get rid of.  Your vegging cannabis plant won’t care a bit,  it will actually explode with growth within the next weeks due to the increased light where it previously received little.\n" +
                "\n" +
                "Extreme trimming like the above example is not recommended during the flowering process but will work fine all the way up until flowering is started.\n" +
                "\n" +
                "If you need to get your plants up to height, there isn't much you can do except wait or add more light.  If you are providing the optimal nutrients your plants will only go as fast as the light you provide allows.  Bigger lights =  bigger plants.  If you want your vegging plants to be ready faster you will need to provide them with an HID light source. Metal Halide lights are recommended for increased vegetative growth. This increased growth comes at the cost of increased power consumption compared to vegging under CFL, Fluorescent, or LED options.\n" +
                "\n" +
                "Larger pots will produce larger plants faster.  If you want a monster 3’ plant to start flowering you can’t put her in a one gallon pot.  Cannabis plants height is directly related to their root space.  Typically one gallon of soil can support 12’’ of vertical vegetative growth.  Therefore if you want 3’ plants by the end of flowering you would need at least a 3 gallon pot for each plant.\n" +
                "\n" +
                "This rule of thumb only works up to roughly 5 gallons.  After that mark the grow medium requirements for height are greatly increased.\n", "6",
                R.drawable.timers));

        // Light Color And Why It Matters
        sectionsCardviewItems.add(new SectionsCardviewItems("7", "Light Color And Why It Matters", "Every light has a color temperature value.  Cannabis likes different color light during its vegetative and flowering cycles.  Find out why and what to look for when purchasing artificial light sources.",
                "When selecting the proper spectrum for your specific need you must always incorporate the correct color of light for the task at hand.\n" +
                "\n" +
                "No bulb will give you the same exact spectrum as the natural sun but you can come pretty close for specific task as long as you pay attention to the color temperature of your bulbs.\n" +
                "\n" +
                "Before selecting a color sit down and think about what part of the growth cycle you are trying to mimic.  If it is vegetative growth you are trying to achieve you want to select a light that has much more blue in it as the sun emits more blue light during the spring and early summer months which the plant uses to get as large as possible before starting to flower.\n" +
                "\n" +
                "For vegetative growth you would want to select a light that is heavier in production of blue light.  Good options for this are Metal Halide and CFL (of the right color temperature) \n" +
                "\n" +
                "The difference in natural light color emitted from the sun varies throughout the seasons based on the relative position of the sun to the earth on its yearly orbit and the earth’s natural tilt.\n" +
                "\n" +
                "If you are trying to mimic the flowering cycle of the plant then you would want to use a light source that produces more red light.  Good options for flowering lights are High Pressure Sodium and  CFL (as long as you get the correct bulb color temperature).  \n" +
                "\n" +
                "Light color is measured using the kelvin temperature scale.  Blue light being on the higher end of the scale (6000k-6500k) and red light being lower on the temperature scale (3500k-2800k). The lower on the scale you go, the more red your lights will produce.\n" +
                "\n" +
                "LED lights are the newest equipment to hit the indoor growing market and quality as well as spectrum output can vary greatly between manufacturers.  LEDs boast the most complete of available spectrums but again this is conditional based on the quality of the manufacturer.\n" +
                "\n" +
                "Although plants like more of one color light at certain times, there is nothing wrong with mixing and matching light sources to create a more well rounded overall spectrum.  Supplementing flowering with a Metal Halide light or spot lighting with a couple strategically placed CFL bulbs can do wonders for trichome production and overall bud growth.", "6",
                R.drawable.color_of_light));

        // How Do I Feed My Plants
        sectionsCardviewItems.add(new SectionsCardviewItems("8", "How Do I Feed My Plants?", "Plants need food just like humans need food.  Learn what to feed your plants and when to feed them.  Do you need organic or chemical fertilizers?  Find out in this section.",
               " ", "7",
                R.drawable.what_to_feed));

        // Contain The Smell
        sectionsCardviewItems.add(new SectionsCardviewItems("9", "Contain The Smell", "One thing that can not be avoided when growing cannabis is the pungent smell it creates.  Unless you want all your neighbors to know what is going on at your house you'd better do something about the stink. Learn how to contain the odor without drawing unwanted attention to your grow.",
                "One thing that can not be avoided when growing cannabis indoors or outdoors is the pungent odor it emits that seems to travel forever, alerting anyone within windshot of your cultivation activities.  Whether you are in a legals state or not, you do not want to be aimlessly filling your neighborhood with the scent of 1000 dead skunks on a pile.\n" +
                "\n" +
                "Smells not only alert law enforcement, they also alert nosey neighbors and potential rippers.  To contain the smell there are several options for both indoor and outdoor growers.\n" +
                "\n" +
                "Indoor growers must use some form of cleaning the smell before exhausting it from their grow space.  This is typically done by scrubbing the air and integrating this within their existing grow ventilation system in the form of a charcoal filter.\n" +
                "\n" +
                "A charcoal filter is an inline filter where air is forced through a thick layer of activated carbon.  Activated carbon eats smells rendering them non-existent.  Where burning incense only masks an odor, activated carbon filters and the other methods we will touch on actually clean the air of the particles rendering the air fresh and clean.\n" +
                "\n" +
                "In order for a charcoal filter to work you must force enough air through it to penetrate the packed activated carbon inside.  This layer is thick and requires a substantial amount of force to get through.  Filters are rated by how much air force is needed to operate the filter adequately.  You must exceed this rating with your ventilation system.  If the charcoal filter requires 70cfm of air you need to push more than 70cfm of air through it in order for it to be effective.\n" +
                "\n" +
                "Carbon filters only clean the air that is moved through them therefore you will need a sealed environment to clean the air from your grow.  This is usually accomplished by making sure that the grow space is air tight when buttoned up and the only way for air to exchange is through the intake and exhaust.  As long as the grow space is sealed so contaminated air can not escape without going through the exhaust, your smell should be contained.\n" +
                "\n" +
                "Carbon filters have a lifespan and to keep your smell control at optimal performance you will need to change out your carbon filters every 6-9 months.  You can choose to either buy a new filter or buy activated carbon and repack the filter yourself.\n" +
                "\n" +
                "There are situations where having a sealed exhaust is not a viable solution or a carbon filter setup needs a little extra help.  You can implement ozone as alternative or additional air cleaning method.  You can either create your own ozone, which is actually (O3) oxygen with an extra oxygen molecule attached.  This heavier molecule binds to the particles containing the stink, eliminating the smell entirely.\n" +
                "\n" +
                "The problem with ozone is that it is not healthy for humans or your plants.  Therefore it is usually implemented outside the actual grow space and not in an area humans inhabit for extended hours.\n" +
                "\n" +
                "Ozone is created with an ozone generator which contains a bulb that converts oxygen gas into (O3). Ozone eats smells bonding to them and causing them to essentially drop to the floor.  Much like carbon filters, Ozone generators have a lifespan as well, the bulbs will eventually burn out, needing replacement.\n" +
                "\n" +
                "Ozone generators can be quite prices for a quality unit.  Another option is to get some ONA gel.  ONA is a jello like substance that gives off ozone gas passively.  ONA is a great product to combat smells that creep out of the grow space.  A small saucer with a spoonful of ONA is usually enough to quell small odors emanating from the grow.\n" +
                "\n" +
                "If your grow is especially pungent you can also mask some odors with strategically placed outdoor plants. Pungent flowers or bushes next to the door to the grow space is always a good option.  You can use this method outdoors as well, but it will be more difficult to create a natural landscaping scene that does not look man made.\n" +
                "\n" +
                "When growing outdoors and away from your manicured lawn, it is almost impossible to contain the smell.  Your best option in this situation is to choose grow spots far away from prying noses.", "8",
                R.drawable.skunk_smell));

        // How To Sex Your Plants
        sectionsCardviewItems.add(new SectionsCardviewItems("10", "How To Sex Your Plants", "Cannabis is requires 2 sexes in order to propagate its genetics further.  Males do not produce the flowers that contain the largest concentration of THC.  So unless you are breeding, males are no good.  Find out how to tell the boys from the girls.",
                "The Article Text Goes Here.", "9",
                R.drawable.male_and_female_plants));

        // Indoor Vs. Outdoor
        sectionsCardviewItems.add(new SectionsCardviewItems("11", "Indoor Vs. Outdoor", "The time honored debate over which is better... growing your cannabis indoors or outdoors.  There are pros and cons to each method.  We will take a look which method is best for your needs and circumstances.",
                "There are upsides and downsides to both growing indoors and outdoors.  The choice of which is better will be determined by your specific situation.   Let’s look at the pro’s and con’s of each method to help you choose which is best for you.\n" +
                "\n" +
                "There is honestly no better place for a cannabis plant than outside in its natural environment.  It is harder to stress a plant under natural conditions than it is in a controlled indoor grow space.  There is no way to compete with the natural light generated by our sun.\n" +
                "\n" +
                "That being said, the same factors that allow for optimum conditions for your cannabis can also expose it to unwanted factors such as bugs and other pest, prying eyes, and even rippers.\n" +
                "\n" +
                "The best thing about growing indoors is the ability to control your grow environment and every aspect of your cannabis’s lifecycle.  It is pretty hard to get a budworm infestation indoors, but it is equally difficult to have a PH issue outdoors.\n" +
                "\n" +
                "The comparison may sound like six of one and half a dozen of the other, but the best choice for your grow will ultimately be determined by your specific situation.\n" +
                "\n" +
                "Indoor grows tend to be much frostier since the plants are not  beaten down by the rain and the wind, conversely it is near impossible to get the density in your buds achievable by outdoor cultivation.\n" +
                "\n" +
                "Indoor cultivation is typically more expensive than outdoor due to the increase in energy needed to run lights and fans that are just not necessary in an outdoor environment.\n" +
                "\n" +
                "Outdoor plants can also take much higher doses of fertilizer than indoor plants because of the sun's ability to increase the maximum nutrient uptake of the plants.\n" +
                "\n" +
                "Indoor environments make it much harder to encounter rippers unless you run your mouth to everyone about how awesome your indoor cannabis grow is doing.  I know you are excited about your crop but if you are smart you will keep it to yourself until after you harvest.  This goes for both indoor and outdoor cultivation.\n" +
                "\n" +
                "Some Quick Notes On Indoor And Outdoor Grows\n" +
                "\n" +
                "Your odds of producing a fruitful crop to harvest is absolutely better indoors.\n" +
                "You can not absolutely control pests outdoors.\n" +
                "There is no better light source than the sun. \n" +
                "PH issues are hard to manifest outdoors.\n" +
                "Buds appear frostier indoors.", "10",
                R.drawable.indoor_vs_outdoor));

        // Watch Your Head
        sectionsCardviewItems.add(new SectionsCardviewItems("12", "Watch Your Head", "Be aware that your plants are going to stretch a lot durring flower, they may even double in size. Find out how to keep your plants at a safe distance from your lights.",
                "The actual article text goes here", "11",
                R.drawable.plants_grown_into_lights));

        // Its Gonna Get Wet
        sectionsCardviewItems.add(new SectionsCardviewItems("13", "It's Gonna Get Wet", "No matter how you do it, growing cannabis involves a sizable amount of water.  Learn how to avoid self induced flooding and water damage.",
                "It’s going to get wet no matter what you do or how hard you prepare.\n " +
                "\n" +
                "It is important to choose the right location when starting an indoor grow.  One aspect that many people initially overlook is the fact that your grow, no matter what medium you choose, is going to require a decent amount of water.\n" +
                "\n" +
                "Whether you have a hose close or transport water in by the bucket, there is no getting around the need for water.  Another thing about water is that it always wants to go down.  This may sound trivial until you start to equate your grow location into the mix.\n" +
                "\n" +
                "If you decide to place your grow in the attic, you can almost certainly ensure that you will one day be painting the ceiling and possibly doing some drywall work.  Why?  Because water will bleed through that ceiling quicker than you can contain it and it is going to happen at the worst possible time, guaranteed.\n" +
                "\n" +
                "Not to mention attics are almost impossible to keep cool in the summer months and difficult to heat in the winter months.  You could also give yourself away when all the snow melts off half of your roof before any of your neighbors snow begins to melt.\n" +
                "\n" +
                "I have watched far too many grows ruin house interiors because of poor placement.  Even when the grower took extra precaution to catch any runoff water, eventually they all failed because as time goes by things in the grow area shift, we become too accustomed to our setup or we’re simply too careless and something happens.\n" +
                "\n" +
                "Your grow is going to leak water, from areas you didn’t plan on having to deal with water.  Do not put your grow above other parts of your living space if you have any choice whatsoever.\n" +
                "\n" +
                "A stealth attic grow can turn not so stealth at all in a matter of minutes just because you choose to put it close to the 2nd floor bathroom instead of carrying water an extra 40 feet if you had placed it in the basement.\n" +
                "\n" +
                "Basements are an ideal indoor grow location not only because the subterranean location will contain your smell and noise from the equipment, but it will also be the most forgiving to unplanned spills and runoff.\n" +
                "\n" +
                "If you can prevent placing your grow above any other living space you will greatly reduce your chance of getting discovered by a careless mistake.  Careless mistakes happen every day to all of us and to think that you are smarter than everyone, you may be caught already.\n" +
                "\n" +
                "If you have no choice but to grow above your living space, plastic the hell out of everything.  I prefer to use thick 6M panda plastic (Black and White Poly).  This is thicker than most bulk plastic options which will help to retain any unforeseen spills. Water will always seek the lowest available space so be sure to extend your plastic vertically a few inches to create a ‘pool’ like container for your plants.  This will ensure that if any water starts to build up it doesn’t immediately spill over into an unwanted area.\n" +
                "\n" +
                "Double lining this barrier is another good practice to help waterproof your grow.  The more layers and the more attention to blocking any potential runoff paths will repay the effort 10 fold as opposed to spending a week remodeling your bathroom because you hastily set up your grow space and figured it was ‘good enough’.\n", "12",
                R.drawable.flood));

        // Importance Of Air Circulation
        sectionsCardviewItems.add(new SectionsCardviewItems("14", "Importance Of Air Circulation", "It can not be stressed enough that to grow quality cannabis indoors requires moving a lot of air.  Find out how much air you need to move and what size fans will get the job done.",
                "The actual article text goes here", "13",
                R.drawable.fan));

        // Dark Means Dark
        sectionsCardviewItems.add(new SectionsCardviewItems("15", "Dark Means Dark", "Cannabis is a photosensitive flowering plant, which means it determines when it is time to flower by the length of its dark period.  Learn everything you need to know about keeping it dark to avoid serious problems down the road.",
                "In order for cannabis to flower it must receive an uninterrupted dark period of at least 12 hours.  During this dark period the plant can not receive even the slightest amount of light whatsoever.  Even a small sliver of light peeking through the smallest crack in your grow space or a distant streetlight can cause a plant to stress and possible turn hermaphrodite.\n" +
                "\n" +
                "Although a hermaphrodite plant will still flower and produce usable buds, you will not be able to achieve a seedless crop if hermaphrodites appear.  This is perhaps the hardest aspect of growing to iron out.\n" +
                "\n" +
                "You can go through a dozen or more genetics before finding a solid strain suitable for indoor growing.  It is much harder to stress an outdoor plant, but any light during your dark period can greatly increase the risk of hermaphrodite plants.\n" +
                "\n" +
                "In order to insure your dark period is actually dark is to sit in your grow space with the lights off and the exterior lights on.  Give yourself 3-5 minutes in the complete darkness to allow your eyes to adjust to the lack of light, this will increase your probability of actually finding a light leak.\n" +
                "\n" +
                "If you allow your eyes time to adjust any light leak should be obvious and immediately apparent.  If you find any leaks you must engineer a way to plug that leak.  Whether it a few pieces of duct tape, some thick plastic, or some other creative solution you come up with, you absolutely must insure that no exterior light enters your grow durring lights out.", "14",
                R.drawable.dark_means_dark));

        // Veg Time
        sectionsCardviewItems.add(new SectionsCardviewItems("16", "Veg Time", "The size of your plant when you initiate the flower cycle will determine the amount of cannabis you harvest.  The time frame between seedling and flowering is called Vegetative time or Veg.  Learn how long you need to veg in order to ensure a bountiful harvest.",
                "It can be excruciating sometimes waiting for your adolescent plants to get big enough to flower.  The size they start to flower will ultimately determine their relative height by harvest.  Generally, the larger the plant, the larger your yield will be.  Although an easy formula for outdoor growing, indoor cultivation often has constraints such as height and width restrictions.\n" +
                "\n" +
                "Vegging requires much more light than flowering.  As cannabis is a photosensitive flowering plant you must keep the daylight to a minimum of 18 hours to ensure they do not start to flower.  Cannabis does not need a dark period during its vegetative state and keeping light on your plants 24/7 is a good option to maximize growth within a certain time period.\n" +
                "\n" +
                "Your plants must be sexually mature to flower which means you will be forced to wait at least 4 to 6 weeks from seed before they will begin to flower, no matter what the light cycle.  A plant is sexually mature when the nodes start to alternate rather than forming in linear sets of two.\n" +
                 "\n" +
                 "That being said you must figure out what your maximum plant height at peak flower will be.  You must account for your lights and some padding for heat just below the bulb.\n" +
                "\n" +
                "Generally a plant will stretch an additional ⅓ to ½ of its size from when flowering is started.  Plant size can also be maintained in conjunction with pot size.  A smaller pot will support a smaller plant than a larger pot.  On average a pot can support 1ft of growth for every gallon of soil up to a certain point.  So if you want 3 foot plants, you should start flowering in 3 gallon pots.  This rule falls apart after about 5 gallons.\n" +
                "\n" +
                "Each strain is going to react slightly different to container size as it relates to plant size and adjustments will need to be made based on your own specific situation.\n" +
                "\n" +
                "Your plants can be kept in a perpetual vegetative cycle and become towering trees if you were so inclined.  Most people would find this overkill but be aware that there is no limit to your plant size before flowering as long as you account for the stretch that will occur during the first 3-4 weeks of flowering.", "15",
                R.drawable.veg_time));

        return sectionsCardviewItems;
    }

    @Override
    public int getColumnNumber() {
        return 1;
    }
}
