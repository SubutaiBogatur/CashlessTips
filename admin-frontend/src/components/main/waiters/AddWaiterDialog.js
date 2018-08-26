import React, {Component} from 'react';
import './AddWaiterDialog.css'
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
    TextField,
    DialogActions,
    Button
} from "@material-ui/core";

export class AddWaiterDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            cardNumber: ''
        }
    }

    save() {
        alert('actual connection to server is wip')
    }

    render() {
        return (
            <Dialog open={this.props.open}
                    onClose={this.props.handleClose}
                    aria-labelledby='form-dialog-title'>
                <DialogTitle id='form-dialog-title'>Добавить официанта</DialogTitle>
                <DialogContent className='dialog'>
                    <DialogContentText>
                        Введите имя официанта и номер его карты. Если вы не привяжете карту официанта или с ней что-то
                        пойдет не так, мы переведем его чаевые на общую карту.
                    </DialogContentText>
                    <div className='edit-dialog-data-wrapper'>
                        <TextField autoFocus
                                   className='edit-dialog-name'
                                   value={this.state.name}
                                   margin='dense'
                                   onChange={event => this.setState({name: event.target.value})}
                                   label='Имя официанта'/>
                        <TextField className='edit-dialog-card-number'
                                   value={this.state.cardNumber}
                                   margin='dense'
                                   onChange={event => this.setState({cardNumber: event.target.value})}
                                   label='Номер карты'/>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.handleClose} color='primary'>
                        Отмена
                    </Button>
                    <Button onClick={() => this.save()}
                            color='primary'>
                        Сохранить
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}