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
import axios from 'axios';
import {Auth} from "../../../util/Auth";

export class AddKktDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            errorDialogOpen: false,
            number: ''
        }
    }

    save() {
        axios.get('/api/setFnInfo', {
            params: {
                inn: Auth.getUsername(),
                fn: this.state.number
            }
        }).then(result => {
            this.props.updateKkts();
            this.props.handleClose();
        }, error => {
            this.setState({
                errorDialogOpen: true
            });
        })
    }

    render() {
        return ([
            <Dialog key='main-dialog'
                    open={this.props.open}
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
                                   label='Номер ККТ (ФН)'/>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.handleClose} color='primary'>
                        Отмена
                    </Button>
                    <Button onClick={() => {
                        this.save();
                    }}
                            color='primary'>
                        Сохранить
                    </Button>
                </DialogActions>
            </Dialog>,
            <Dialog key='err-dialog'
                    open={this.state.errorDialogOpen}
                    onClose={() => this.setState({errorDialogOpen: false})}
                    aria-labelledby='alert-dialog-title'
                    aria-describedby='alert-dialog-description'>
                <DialogTitle id='alert-dialog-title'>
                    Неверный формат ФН!
                </DialogTitle>
                <DialogActions>
                    <Button onClick={() => this.setState({errorDialogOpen: false})} color='primary'>
                        ОК
                    </Button>
                </DialogActions>
            </Dialog>
        ])
    }
}