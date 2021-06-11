package bman.scrp.persist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileQueueRepository extends JpaRepository<FileQueue, Long> {
	
	List<FileQueue> findByStatus (String status);
	Optional<FileQueue> findFirstByStatus (String status);
	Optional<FileQueue> findByFilename (String filename);
	
}
