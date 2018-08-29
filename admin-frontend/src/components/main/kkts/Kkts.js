import React, {Component} from 'react';
import './Kkts.css';
import {
    Button,
    ListItem,
    ListItemAvatar,
    Avatar,
    ListItemText,
    IconButton,
    Divider,
    List,
    ListItemSecondaryAction
} from "@material-ui/core";
import DeleteIcon from '@material-ui/icons/Delete'
import PaymentIcon from '@material-ui/icons/Payment'
import axios from 'axios'
import {AddKktDialog} from "./AddKktDialog";

export class Kkts extends Component {
    constructor(props) {
        super(props);
        this.state = {
            addDialogOpen: false,
            kkts: null
        }
    }

    componentWillMount() {
        this.loadKkts();
    }

    loadKkts() {
        axios.get('/api/listFnByInn', {
            params: {
                inn: this.props.inn
            }
        }).then(res => {
            this.setState({
                kkts: res.data.map(entry => {
                    return {
                        id: entry.fn,
                        number: entry.fn
                    }
                })
            });
        })
    }

    render() {
        return (
            <div className='kkts-main'>
                <div className='kkts-add-button-wrapper'>
                    <Button variant='raised'
                            color='primary'
                            onClick={() => this.setState({addDialogOpen: true})}>
                        Добавить ККТ
                    </Button>
                </div>
                <div className='kkts-list-wrapper'>
                    <List className='kkts-list'>
                        {this.state.kkts != null && this.state.kkts.map((kkt, i, arr) => ([
                                <ListItem key={kkt.id}
                                          className='kkts-list-item'>
                                    <ListItemAvatar>
                                        <Avatar>
                                            <PaymentIcon/>
                                        </Avatar>
                                    </ListItemAvatar>
                                    <ListItemText
                                        primary={'ККТ ' + kkt.number}
                                        // secondary={project.manager_chat === null ?
                                        //     null : 'Manager chat: ' + project.manager_chat.title}
                                    />
                                    <ListItemSecondaryAction>
                                        <IconButton aria-label='Delete'
                                            // onClick={() => this.handleDeleteButton(project)}
                                        >
                                            <DeleteIcon/>
                                        </IconButton>
                                    </ListItemSecondaryAction>
                                </ListItem>,
                                i === arr.length - 1 ? null : <Divider key={kkt.id + '_divider'} inset component='li'/>
                            ])
                        )}
                    </List>
                </div>
                <AddKktDialog open={this.state.addDialogOpen}
                              updateKkts={() => this.loadKkts()}
                              handleClose={() => this.setState({addDialogOpen: false})}/>
            </div>
        )
    }
}