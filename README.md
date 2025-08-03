# 📋 Development Plan

### ✅ Phase 1: Core Logic

1. `TreeNode.java` – defines the binary tree node structure
2. `TreeService.java` – handles insertion and (optionally) balancing

### ✅ Phase 2: Database Layer

3. `TreeEntity.java` – JPA entity for storing numbers + tree JSON
4. `TreeRepository.java` – JPA repository for accessing stored trees

### ✅ Phase 3: Controller Layer

5. `TreeController.java` – defines endpoints:
   - `/enter-numbers`
   - `/process-numbers`
   - `/previous-trees`

### ✅ Phase 4: Frontend Templates

6. `templates/enter-numbers.html` – number input form
7. `templates/previous-trees.html` – list previous inputs/trees

### ✅ Phase 5: Testing

8. `TreeServiceTest.java` – unit tests:
   - Tree insertion
   - JSON serialization
   - Optional: balancing

### ✅ Phase 6: Config & Main

9. `application.properties` – H2 DB config
10. `BstApplication.java` – Spring Boot entry point
