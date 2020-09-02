export default {
  ACCESS_GRANTED: {
    icon: 'success',
    title: 'Bienvenido',
    text: 'Acceso autorizado',
    showConfirmButton: false,
    timer: 1000,
    timerProgressBar: true 
  },

  SIGN_IN_SUCCEED: {
    icon: 'success',
    title: 'Bienvenido',
    text: 'Ha sido registrado exitosamente'
  },

  ACCESS_DENIED: {
    icon: 'error',
    title: 'Credenciales incorrectas',
    text: 'Acceso no autorizado',
  },

  EMAIL_IN_USE: {
    icon: 'error',
    title: 'Email en uso',
    text: 'El correo electrónico indicado ya se encuentra en uso. Por favor ingrese otro para continuar'
  },

  FORM_NOT_COMPLETE: {
    icon: 'warning',
    title: 'Formulario incompleto',
    text: 'Por favor, revise los campos',
  },

  NOT_MATCHING_PASS: {
    icon: 'warning',
    title: 'Las contraseñas no coinciden',
    text: 'Por favor, comprueba los valores introducidos'
  },

  GENERIC_ERROR: {
    icon: 'error',
    title: 'Error',
    text: 'Ha ocurrido un error, inténtelo de nuevo o más tarde',
    showConfirmButton: false,
    timer: 2000,
    timerProgressBar: true 
  }
}