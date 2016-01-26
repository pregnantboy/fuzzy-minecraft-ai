//
//  EditorViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "EditorViewController.h"
#import "DropDownTableViewCell.h"
#import "MainMenuViewController.h"
#import "ToggleTableViewCell.h"

@interface EditorViewController () {
    Rule *rule;
}

@end

@implementation EditorViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.background setAlphaValue:0.9];
    [self.view addSubview:self.background positioned:NSWindowBelow relativeTo:nil];
    [MainMenuViewController addShadow:self.backButton];
    [MainMenuViewController addShadow:self.addButton];
    [MainMenuViewController addShadow:self.deleteButton];
    rule = [[Rule alloc] init];
    [self.backButton setAction:@selector(goToRulesListPage)];
    [self.addButton setAction: @selector(addNewRule)];
    [self.deleteButton setAction: @selector(removeRule)];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(onConditionChanged:)
                                                 name:@"ConditionChanged"
                                               object:nil];
    [self tempOutcome1];
    [self tempOutcome2];
}

- (void)loadRule:(Rule *)ruleToLoad {
    rule = ruleToLoad;
}

#pragma mark - NSTableView delegates

- (BOOL)tableView:(NSTableView *)aTableView shouldSelectRow:(NSInteger)rowIndex {

    return YES;
}

- (NSInteger)numberOfRowsInTableView:(NSTableView *)tableView {
    return [rule getNumInputs];
}

- (CGFloat)tableView:(NSTableView *)tableView
         heightOfRow:(NSInteger)row {
    return 50;
}

- (NSView *)tableView:(NSTableView *)tableView
   viewForTableColumn:(NSTableColumn *)tableColumn
                  row:(NSInteger)row {
    
    // Retrieve to get the @"MyView" from the pool or,
    // if no version is available in the pool, load the Interface Builder version
    if ([tableColumn.identifier isEqualToString:@"first"]) {
        ToggleTableViewCell *result = (ToggleTableViewCell *) [tableView makeViewWithIdentifier:@"ToggleTableViewCell" owner:self];
        if (row == 0) {
            [result setType:0];
        } else {
            [result setType:1];
            [result setString:[rule getOperatorAtIndex:row]];
        }
        return result;
    }
    
    else if ([tableColumn.identifier isEqualToString:@"second"]) {
        DropDownTableViewCell *result = (DropDownTableViewCell *) [tableView makeViewWithIdentifier:@"DropDownTableViewCell" owner:self];
        [result setType:2];
        [[result popUp] setTag:row];
        NSString *condition = [rule getCondAtIndex:row];
        if (condition != nil) {
            [result setString:condition];
        }
        return result;
    }
    
    else if ([tableColumn.identifier isEqualToString:@"third"]) {
        ToggleTableViewCell *result = (ToggleTableViewCell *) [tableView makeViewWithIdentifier:@"ToggleTableViewCell" owner:self];
        [result setType:3];
        NSString *eq = [rule getEqualityAtIndex:row];
        [result setString: eq];
        return result;
    }
    else{
        DropDownTableViewCell *result = (DropDownTableViewCell *) [tableView makeViewWithIdentifier:@"DropDownTableViewCell" owner:self];
        [result setType:4];
        [[result popUp] setTag:row];
        NSString *condition = [rule getCondAtIndex:row];
        if (condition != nil) {
            [result enableValueButtonForCond:condition];
            NSString *value = [rule getValueAtIndex:row];
            if (value != nil) {
                [result setString: value];
            }
        }
        return result;
    }
}

#pragma mark - IBAction 

- (IBAction)goToRulesListPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *vc = [mainsb instantiateControllerWithIdentifier:@"RulesListViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
}

- (IBAction)addNewRule {
    if ([[rule getRuleAtIndex:[rule getNumInputs] -1] isSet]){
        [rule createNewRuleInput];
        [self.inputTableView reloadData];
    }
}

- (IBAction)removeRule {
    if ([rule getNumInputs] >1) {
        if ([self.inputTableView selectedRow] >= 0) {
            [rule deleteRuleInput:[self.inputTableView selectedRow]];
           
        } else {
            [rule deleteRuleInput:[rule getNumInputs] -1];
        }
        [self.inputTableView reloadData];
    }
}

#pragma mark - Event Handlers
- (void) onConditionChanged:(NSNotification *) notification {
    DropDownTableViewCell *object = (DropDownTableViewCell*) [notification object];
    if ([object type] == 2) {
        [rule setCond:[object getString] atIndex:[object getRow]];
        [rule setValue:nil atIndex:[object getRow]];
    } else {
        [rule setValue:[object getString] atIndex:[object getRow]];
    }
    [self.inputTableView reloadData];
}

- (void)tempOutcome1 {
    NSMenu *menu = [[NSMenu alloc] init];
    NSMenuItem *placeholderItem = [[NSMenuItem alloc] initWithTitle:@"Select Action" action:nil keyEquivalent:@""];
    [placeholderItem setAttributedTitle:[DropDownTableViewCell changeToWhiteText:@"Select Action" withSize:26]];
    [placeholderItem setHidden:YES];
    [menu addItem:placeholderItem];
    NSArray *values = @[@"Attack Nearest Enemy", @"Attack Player's Target", @"Run Away", @"Sow Seeds", @"Build House", @"Mine Ores"];
    for (NSString *val in values) {
        NSMenuItem *item = [[NSMenuItem alloc] initWithTitle:val action:nil keyEquivalent:@""];
        [item setAttributedTitle:[DropDownTableViewCell changeToWhiteText:val withSize:26]];
        [menu addItem:item];
    }
    [self.outcome1 setEnabled:YES];
    [self.outcome1 setMenu:menu];
    [self.outcome1 selectItemWithTitle:@"Select Action"];
}

- (void)tempOutcome2 {
    NSMenu *menu = [[NSMenu alloc] init];
    NSMenuItem *placeholderItem = [[NSMenuItem alloc] initWithTitle:@"Select Action Modifier" action:nil keyEquivalent:@""];
    [placeholderItem setAttributedTitle:[DropDownTableViewCell changeToWhiteText:@"Select Action Modifier" withSize:26]];
    [placeholderItem setHidden:YES];
    [menu addItem:placeholderItem];
    NSArray *values = @[@"", @"With Melee Weapon", @"With Arrow", @"With Fireballs", @"of Wheat"];
    for (NSString *val in values) {
        NSMenuItem *item = [[NSMenuItem alloc] initWithTitle:val action:nil keyEquivalent:@""];
        [item setAttributedTitle:[DropDownTableViewCell changeToWhiteText:val withSize:26]];
        [menu addItem:item];
    }
    [self.outcome2 setEnabled:YES];
    [self.outcome2 setMenu:menu];
    [self.outcome2 selectItemWithTitle:@"Select Action Modifier"];
}


@end
