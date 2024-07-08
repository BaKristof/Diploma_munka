package com.example.myapplication.Enemys;

import com.example.myapplication.SuperClasses.EnemyCharacter;
import com.example.myapplication.Enemys.Spawners.Spawner;

public class EnemyCharacterWithOwner {
    EnemyCharacter enemyCharacter;
    Spawner owner;

    public EnemyCharacterWithOwner(EnemyCharacter enemyCharacter, Spawner owner) {
        this.enemyCharacter = enemyCharacter;
        this.owner = owner;
    }

    public EnemyCharacter getEnemyCharacter() {
        return enemyCharacter;
    }

    public void setEnemyCharacter(EnemyCharacter enemyCharacter) {
        this.enemyCharacter = enemyCharacter;
    }

    public Spawner getOwner() {
        return owner;
    }

    public void setOwner(Spawner owner) {
        this.owner = owner;
    }
    public EnemyCharacter asEnemyCharcater(){
        return enemyCharacter;
    }
}
