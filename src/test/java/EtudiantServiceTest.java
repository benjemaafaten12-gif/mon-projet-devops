
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("John Doe");
        etudiant.setCin(123456);
    }

    @Test
    void testAddOrUpdate() {
        // Arrange
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addOrUpdate(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getNomEt());
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    void testFindAll() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant));

        // Act
        List<Etudiant> etudiants = etudiantService.findAll();

        // Assert
        assertNotNull(etudiants);
        assertEquals(1, etudiants.size());
        assertEquals("John Doe", etudiants.get(0).getNomEt());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getNomEt());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        // Act
        etudiantService.deleteById(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        // Act
        etudiantService.delete(etudiant);

        // Assert
        verify(etudiantRepository, times(1)).delete(etudiant);
    }
}