# CarManagementSystem
CarManagementSystem is a Maven Multi Module project written in Java 12, that enables to manage data about cars.\
It allows user to : 
* Sort cars by: 
    * Model name
    * Color
    * Price
    * Mileage
* Show cars with higher mileage than given as argument
* Show juxtaposition with quantity of cars with each color
* Show juxtaposition with most expensive car in each model
* Show summary statistics for price and mileage that contains average, maximum and minimum value
* Show most expensive car
* Show cars with components sorted in alphabetical order
* Show juxtaposition with car component and list of cars, that contains this component
* Show cars with given as arguments price range

## Installation

* From _CarManagementSystem_ module: 
```bash
    mvn clean install
``` 
* From _main_ module
```bash
    mvn clean compile assembly::single
```

## Usage

* From _main/target_ 
```bash
    java --enable-preview -cp main-1.0-SNAPSHOT-jar-with-dependencies.jar stefanowicz.kacper.main.App
```

Please make sure that _cars.json_ file is located in the same folder as _main-1.0-SNAPSHOT-jar-with-dependencies.jar_
