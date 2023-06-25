# Storage Cleaner (Duplicate File Remover)

## Project Proposal

The project proposal can be found [here](
    https://www.overleaf.com/read/bjhkktddkfdz
).

## Project Report

The project report can be found [here](
    https://www.overleaf.com/read/xkjhmxfcdnhr
).

## Usage

### Requirements

- Java 8 or higher
- Visual Studio Code (recommended)
- Java Extension Pack (recommended)

### Running the program

- Open the project in Visual Studio Code
- Open the `src` folder
- Open the `DumpFiles.java` file
- Click on the `Run` button on the top right corner of the editor 
- The program will dump a dummy folder structure in the `data/` folder
- Open the `StorageCleaner.java` file
- Click on the `Run` button on the top right corner of the editor
- The program will run and ask for the path of the folder to be cleaned
- Enter the path of the folder to be cleaned (the `data/` folder in this case)
- The program will run and print the directories and files as a tree and files kept and deleted
- The program will also print the time taken to run the program
- The program will also print the space saved by deleting duplicate files

### Running the tests

- Some java files have tests direclty written in them for benchmarking
- Open the `src` folder
- Open the `Helpers/Hashing.java` file
- Click on the `Run` button on the top right corner of the editor
- The program will run and print the time taken to hash n number of files