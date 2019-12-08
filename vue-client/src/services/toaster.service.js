import { ToastProgrammatic as Toast } from 'buefy'

export default {
    success(message) {
        Toast.open({
            message: message,
            position: 'is-bottom',
            type: 'is-success'
        });
    },

    error(message) {
        Toast.open({
            duration: 10000,
            message: message,
            position: 'is-bottom',
            type: 'is-danger'
        });
    }
}