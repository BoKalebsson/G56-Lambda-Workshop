package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods.
 * I have already provided an implementation for the first method.
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl() {
        personList = PersonGenerator.getInstance().generate(1000);
    }


    static DataStorage getInstance() {
        return INSTANCE; // Returning the singleton instance
    }

    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        List<Person> result = new ArrayList<>(); // Creating a list to store filtered Person objects.
        for (Person person : personList) { // Iterating over each Person object in personList.
            if (filter.test(person)) { // Testing if the Person object satisfies the filter predicate.
                result.add(person); // Adding the Person object to the result list if it satisfies the predicate.
            }
        }
        return result; // Returning the list of filtered Person objects.
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        for (Person person : personList) { // Iterating over each Person object in personList.
            if (filter.test(person)){ // Testing if the Person object satisfies the filter predicate.
                return person; // Return the Person object if it satisfies the predicate.
            }
        }
        return null; // Noone found matching the predicate.
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString) {
        for (Person person : personList) { // Iterating over each Person object in personList.
            if (filter.test(person)){ // Testing if the Person object satisfies the filter predicate.
                return personToString.apply(person); // Return the Person object using personToString() if it satisfies the predicate.
            }
        }
        return null; // Noone found matching the predicate.
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {
        List<String> result = new ArrayList<>(); // Creating a list to store filtered Person objects as Strings.
        for (Person person : personList) { // Iterating over each Person object in personList.
            if (filter.test(person)){ // Testing if the Person object satisfies the filter predicate.
                result.add(personToString.apply(person)); // Apply personToString on Person objects and add to the String list.
            }
        }
        return result; // Returning the string list of filtered Person objects.
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer) {
        for (Person person : personList) { // Iterating over each Person object in personList.
            if (filter.test(person)){ // Testing if the Person object satisfies the filter predicate.
                consumer.accept(person); // Apply an action to the person.
            }
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator) {
        List<Person> sortedPersons = new ArrayList<>(personList); // Make a copy of the original list of Persons.
        sortedPersons.sort(comparator); // Sort the copy using the provided comparator.
        return sortedPersons; // Return the sorted list.
    }

    @Override
    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator) {
        List<Person> filteredPersons = findMany(filter); // Reuse our filter-logic.
        filteredPersons.sort(comparator); // Sort with provided comparator
        return filteredPersons; // Return the filtered list.
    }
}
