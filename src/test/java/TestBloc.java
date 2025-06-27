
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestBloc {
    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
    }

    @Test
    void testAddOrUpdate() {
        // Arrange
        Bloc bloc = new Bloc();
        List<Chambre> chambres = new ArrayList<>();
        Chambre chambre1 = new Chambre();
        chambres.add(chambre1);
        bloc.setChambres(chambres);

        when(blocRepository.save(bloc)).thenReturn(bloc);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre1);

        // Act
        Bloc result = blocService.addOrUpdate(bloc);

        // Assert
        assertNotNull(result);
        verify(blocRepository, times(1)).save(bloc);
        verify(chambreRepository, times(1)).save(chambre1);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc();
        blocs.add(bloc1);
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.findAll();

        // Assert
        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        long id = 1L;
        Bloc bloc = new Bloc();
        when(blocRepository.findById(id)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.findById(id);

        // Assert
        assertNotNull(result);
        verify(blocRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        // Arrange
        long id = 1L;

        // Act
        blocService.deleteById(id);

        // Assert
        verify(blocRepository, times(1)).deleteById(id);
    }

    @Test
    void testAffecterBlocAFoyer() {
        // Arrange
        String nomBloc = "Bloc A";
        String nomFoyer = "Foyer 1";
        Bloc bloc = new Bloc();
        Foyer foyer = new Foyer();

        when(blocRepository.findByNomBloc(nomBloc)).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer(nomFoyer)).thenReturn(foyer);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.affecterBlocAFoyer(nomBloc, nomFoyer);

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(blocRepository, times(1)).findByNomBloc(nomBloc);
        verify(foyerRepository, times(1)).findByNomFoyer(nomFoyer);
        verify(blocRepository, times(1)).save(bloc);
    }

}
