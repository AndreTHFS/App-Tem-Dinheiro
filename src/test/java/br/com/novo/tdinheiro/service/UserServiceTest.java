package br.com.novo.tdinheiro.service;

import br.com.novo.tdinheiro.dto.CreateUserDto;
import br.com.novo.tdinheiro.entity.User;
import br.com.novo.tdinheiro.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<User> argumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    private ArgumentCaptor<String>  stringArgumentCaptor;

    @Nested
    class createUser{
        @Test
        @DisplayName("Deve Criar usuário com sucesso ")
        void deveCriarUsuarioComSucesso() {

            CreateUserDto userDto = new CreateUserDto("Antonio","123");
            User user = new User();
            user.setUsername("Antonio");
            user.setPassword("encodedPass");
            when(passwordEncoder.encode("123")).thenReturn("encodedPass");
            when(userRepository.save(any(User.class))).thenReturn(user);
            User createdUser = userService.createUser(userDto);
            assertEquals("Antonio", createdUser.getUsername());
            assertEquals("encodedPass", createdUser.getPassword());
        }

        @Test
        void develancarExercao(){

            var userDto = new CreateUserDto("Antonio", "1234");
            doReturn(true).when(userRepository).existsByUsername(stringArgumentCaptor.capture());
            assertThrows(RuntimeException.class, () -> userService.createUser(userDto));
            assertEquals(userDto.username(),stringArgumentCaptor.getValue());
        }
    }

    @Nested
    class findById{

        @Test
        void deveRetornaUsuarioPorIdComSucessoOptionalPresente(){

            var user = new User(UUID.randomUUID(),"Antonio","123" );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            var output = userService.findById(user.getUserId());

            assertNotNull(output);
            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());
        }
        @Test
        void deveRetornaExercaoIllegalArgumentException(){

            var user = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            assertThrows(EntityNotFoundException.class, () -> userService.findById(user));
            assertEquals(user,uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class findAll{

        @Test
        void retornaListaComSucesso(){
            var user = new User(UUID.randomUUID(),"Antonio","123" );
            doReturn(List.of(user))
                    .when(userRepository)
                    .findAll();
            var output = userService.findAll();
            assertNotNull(output);
            assertEquals(List.of(user).size(), output.size());
        }

    }

    @Nested
    class DeleteById{

        @Test
        void usuarioDeletadoComSucesso() {

            //Arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());
            //Act
            var user = UUID.randomUUID();
            userService.deleteUserById(user.toString());
            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(user, idList.getFirst());
            assertEquals(user, idList.getLast());
        }

        @Test
        void usuarioNaoExisteParaSerDeletado(){

            //Arrange
            var user = UUID.randomUUID();
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            //Act


            //Assert
            assertThrows( EntityNotFoundException.class, () -> userService.deleteUserById(user.toString()));
            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(uuidArgumentCaptor.getValue());
        }
    }
}