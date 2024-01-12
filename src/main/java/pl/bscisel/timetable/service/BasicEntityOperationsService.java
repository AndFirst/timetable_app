package pl.bscisel.timetable.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BasicEntityOperationsService<Entity> {
    /**
     * Searches for entities based on the provided filter.
     *
     * @param filter The filter to apply to the search. If null or empty, returns all entities.
     * @return A list of entities that match the search criteria.
     */
    List<Entity> search(@Nullable String filter);

    /**
     * Saves the provided entity.
     *
     * @param entity The entity to be saved.
     */
    void save(@NotNull Entity entity);

    /**
     * Deletes the provided entity.
     *
     * @param entity The entity to be deleted.
     */
    void delete(@NotNull Entity entity);

    /**
     * Creates an empty entity.
     *
     * @return The empty entity.
     */
    Entity createEmpty();


    /**
     * Returns the class of the entity.
     *
     * @return The class of the entity.
     */
    Class<Entity> getEntityClass();
}
