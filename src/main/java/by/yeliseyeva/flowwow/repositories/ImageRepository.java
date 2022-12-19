package by.yeliseyeva.flowwow.repositories;

import by.yeliseyeva.flowwow.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
