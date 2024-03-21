/** By YamiY Yaten */
package com.yatensoft.dcbot.dto.fab;

public class FabLivingLegendElementDTO {
    private String rank;
    private String hero;
    private Integer seasonPoints;
    private Integer livingLegendPoints;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public int getSeasonPoints() {
        return seasonPoints;
    }

    public void setSeasonPoints(int seasonPoints) {
        this.seasonPoints = seasonPoints;
    }

    public int getLivingLegendPoints() {
        return livingLegendPoints;
    }

    public void setLivingLegendPoints(int livingLegendPoints) {
        this.livingLegendPoints = livingLegendPoints;
    }

    @Override
    public String toString() {
        return "FabLivingLegendElementDTO{" + "rank='"
                + rank + '\'' + ", hero='"
                + hero + '\'' + ", seasonPoints="
                + seasonPoints + ", livingLegendPoints="
                + livingLegendPoints + '}';
    }
}
