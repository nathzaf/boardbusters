package io.github.oliviercailloux.boardbusters.wonder;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.List;

public class Wonder {

    private final String name;

    private final Multiset<Resources> producedResources;

    private final List<WonderStage> wonderStages;

    private int currentStage;

    /**
     * Creates a new Wonder with the specified name, produced resources, and wonder stages.
     *
     * @param name              the name of the Wonder
     * @param producedResources the resources produced by the Wonder
     * @param wonderStages      the stages of the Wonder
     */
    public Wonder(String name, Multiset<Resources> producedResources, List<WonderStage> wonderStages){
        this.name = name;
        this.producedResources = producedResources;
        this.wonderStages = List.copyOf(wonderStages);
        this.currentStage = 0;
    }

    /**
     * Returns a string representation of the resources produced by the Wonder.
     *
     * @return the string representation of produced resources
     */
    public String displayProducedResources(){
        StringBuilder producedResourcesBuilder = new StringBuilder(name + " produces:" + System.lineSeparator());
        for(Resources resources : producedResources.elementSet()){
            producedResourcesBuilder.append(resources.name())
                    .append(": ")
                    .append(producedResources.count(resources))
                    .append(System.lineSeparator());
        }
        return producedResourcesBuilder.toString();
    }

    public boolean isComplete(){
        return currentStage == wonderStages.size();
    }

    public String getName(){
        return name;
    }

    public Multiset<Resources> getProducedResources(){
        return producedResources;
    }

    public List<WonderStage> getStages(){
        return List.copyOf(wonderStages);
    }

    public int getCurrentStage(){
        return currentStage;
    }

    public int getNumberOfStages(){
        return wonderStages.size();
    }

    public void incrementCurrentStage(){
        currentStage++;
    }
}
