import React, {Component} from 'react';
import './AddKktDialog.css';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    TextField
} from "@material-ui/core";

export class AddKktDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            number: ''
        }
    }

    save() {
        alert('actual connection to server is wip')
    }

    render() {
        return (
            <Dialog open={this.props.open}
                    onClose={this.props.handleClose}
                    aria-labelledby='add-kkt-title'>
                <DialogTitle id='add-kkt-title'>Добавить ККТ</DialogTitle>
                <DialogContent className='add-kkt-dialog'>
                    <DialogContentText>
                        Введите номер ККТ. Когда клиент произведет оплату с помощью этого ККТ, мы сможем
                        идентифицировать вашу организацию и перечислить чаевые на ваш счет.
                    </DialogContentText>
                    <div className='add-kkt-data-wrapper'>
                        <TextField autoFocus
                                   className='add-kkt-number'
                                   value={this.state.number}
                                   margin='dense'
                                   onChange={event => this.setState({number: event.target.value})}
                                   label='Номер ККТ (ФН или чо там)'/>
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